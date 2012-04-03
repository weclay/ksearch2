package com.weclay.ksearch2;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.weclay.ksearch2.BasicKoreanAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

/**
 * Unit test for BasicKoreanAnalyzer.
 */
public class BasicKoreanAnalyzerTest
extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public BasicKoreanAnalyzerTest( String testName )
	{
		super( testName );
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( BasicKoreanAnalyzerTest.class );
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testBasicKoreanAnalyzer() throws IOException
	{
		// text to tokenize
		final String text = "그러면 조개가 쏘옥 올라온다";
		//String text = "※ 청년,창직,창업 인턴제";
		//String text = "저는 대학생이구요. 소프트웨어 관련학과 입니다. DB는 수업을 한번 들은 적이 있으며, 수학은 대학에서 통계학, 선형대수학, 이산수학, 대학수학 등을 배웠지만... 자주 사용을 안하다보니 모두 까먹은 상태입니다.";

		BasicKoreanAnalyzer analyzer = new BasicKoreanAnalyzer();
		TokenStream stream = analyzer.tokenStream("field", new StringReader(text));

		// get the TermAttribute from the TokenStream
		TermAttribute termAtt = (TermAttribute) stream.addAttribute(TermAttribute.class);
		OffsetAttribute offsetAtt = (OffsetAttribute) stream.addAttribute(OffsetAttribute.class);

		stream.reset();
		
		int[] lengths = new int[4];
		lengths[0] = 3;
		lengths[1] = 2;
		lengths[2] = 2;
		lengths[3] = 3;
		int i = 0;

		// print all tokens until stream is exhausted
		while (stream.incrementToken()) {
			//System.out.println(termAtt.term() + ": " + termAtt.termLength() + " (" + offsetAtt.startOffset() + ":" + offsetAtt.endOffset() + ")");
			Assert.assertEquals(lengths[i], termAtt.termLength());
			i++;
		}

		stream.end();
		stream.close();
	}
}
