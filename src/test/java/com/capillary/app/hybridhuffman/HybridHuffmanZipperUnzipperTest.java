package com.capillary.app.hybridhuffman;

import com.capillary.app.general.ComplexReturnType;
import com.capillary.app.general.FileRead;
import com.capillary.app.general.FileWrite;
import com.capillary.app.scaledhuffman.ScaledHuffmanZipperUnzipper;
import com.capillary.app.zipper.compression.ICompression;
import com.capillary.app.zipper.compression.ICompressionTree;
import com.capillary.app.zipper.decompression.IDecompression;
import com.capillary.app.zipper.decompression.IDecompressionTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class HybridHuffmanZipperUnzipperTest {

    @Mock
    FileRead fileReaderMock;
    @Mock
    ICompressionTree compTreeMock;
    @Mock
    ICompression compMock;
    @Mock
    FileWrite fileWriterMock;
    @Mock
    IDecompressionTree decompTreeMock;
    @Mock
    IDecompression decompMock;

    @InjectMocks
    HybridHuffmanZipperUnzipper zipper;


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testCompress_NormalCase() throws IOException {
        String originalFile = "input.txt";
        String compressedFile = "compress.txt";

        zipper.compress(originalFile, compressedFile);

        InOrder checkInOrder= inOrder(fileReaderMock, compTreeMock, compMock, fileWriterMock, decompTreeMock, decompMock);
        checkInOrder.verify(fileReaderMock).readComp(any());
        checkInOrder.verify(compTreeMock).getFrequencyMap(any());
        checkInOrder.verify(compTreeMock).generateTree(any());
        checkInOrder.verify(compTreeMock).getHashTable(any());
        checkInOrder.verify(compMock).getCompressedBytes(any(), any());
        checkInOrder.verify(compMock).byteFromByteList(any());
        checkInOrder.verify(fileWriterMock).writeComp(any(), any(), any());
    }

    @Test
    public void testCompress_ThrowsException() throws IOException {
        String originalFile = "input.txt";
        String compressedFile = "compress.txt";

        doThrow(IOException.class).when(fileReaderMock).readComp(any());

        assertThrows(RuntimeException.class,()->zipper.compress(originalFile, compressedFile));
    }

    @Test
    public void testDecompress_NormalCase() throws IOException {
        String originalFile = "input.txt";
        String compressedFile = "compress.txt";

        doReturn(new ComplexReturnType<>(null,null)).when(fileReaderMock).readDecomp(any());

        zipper.decompress(originalFile, compressedFile);

        InOrder checkInOrder= inOrder(fileReaderMock, compTreeMock, compMock, fileWriterMock, decompTreeMock, decompMock);
        checkInOrder.verify(fileReaderMock).readDecomp(any());
        checkInOrder.verify(decompTreeMock).regenerateTree(any());;
        checkInOrder.verify(decompMock).getDecompressedBytes(any(), any(), anyLong());
        checkInOrder.verify(fileWriterMock).writeDecomp(any(), anyBoolean(), any());
    }

    @Test
    public void testDecompress_ThrowsException() throws IOException {
        String compressedFile = "compress.txt";
        String decompressFile = "decompress.txt";

        doReturn(new ComplexReturnType<>(null,null)).when(fileReaderMock).readDecomp(any());
        doThrow(IOException.class).when(fileWriterMock).writeDecomp(any(),anyBoolean(),any());

        assertThrows(RuntimeException.class,()->zipper.decompress(compressedFile, decompressFile));
    }

    @Test
    public void testWeightedAvg() {
        Map<String, Integer> mp = new HashMap<>();
        mp.put("a", 3);
        mp.put("b", 2);
        mp.put("c", 1);

        Map<String, String> hash = new HashMap<>();
        hash.put("a", "0");
        hash.put("b", "11");
        hash.put("c", "10");

        double expected = (double) 9/6;
        double result = zipper.WAvg(mp, hash);

        assertEquals(expected, result, 0.0);
    }
}