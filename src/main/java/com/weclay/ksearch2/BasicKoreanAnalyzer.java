package com.weclay.ksearch2;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;


public final class BasicKoreanAnalyzer extends Analyzer {
	public TokenStream tokenStream(String fieldName, Reader reader) {
		TokenStream stream = new WhitespaceTokenizer(reader);
		stream = new KoreanStemFilter(stream, "ksearch_dic/");
		return stream;
	}

	public static void main(String[] args) throws IOException {
		// text to tokenize
		//final String text = "그러면 조개가 쏘옥 올라온다";
		//String text = "※ 청년,창직,창업 인턴제";
		String text = "저는 대학생이구요. 소프트웨어 관련학과 입니다. DB는 수업을 한번 들은 적이 있으며, 수학은 대학에서 통계학, 선형대수학, 이산수학, 대학수학 등을 배웠지만... 자주 사용을 안하다보니 모두 까먹은 상태입니다.";

		BasicKoreanAnalyzer analyzer = new BasicKoreanAnalyzer();
		TokenStream stream = analyzer.tokenStream("field", new StringReader(text));

		// get the TermAttribute from the TokenStream
		TermAttribute termAtt = (TermAttribute) stream.addAttribute(TermAttribute.class);
		OffsetAttribute offsetAtt = (OffsetAttribute) stream.addAttribute(OffsetAttribute.class);

		stream.reset();

		// print all tokens until stream is exhausted
		while (stream.incrementToken()) {
			System.out.println(termAtt.term() + ": " + termAtt.termLength() + " (" + offsetAtt.startOffset() + ":" + offsetAtt.endOffset() + ")");
		}

		stream.end();
		stream.close();
	}
}
