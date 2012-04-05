/**
 * Copyright 2012 Weclay Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weclay.ksearch2;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.snu.ids.ha.dic.Dictionary;

/**
 * Creates new instances of {@link KoreanStemFilter}.
 */
public class KoreanStemFilterFactory extends BaseTokenFilterFactory {
	private String dicRoot = "./dic";
	
	@Override
		public void init(Map<String, String> args) {
			super.init(args);
			dicRoot = args.get("dicRoot");
			if (dicRoot == null)
				dicRoot = "ksearch_dic/";
			System.setProperty("dicRoot", dicRoot);
			// Pre initialize the dictionary. (Pre loading)
			Dictionary.getInstance();
		}

	public KoreanStemFilter create(TokenStream input) {
		return new KoreanStemFilter(input, dicRoot);
	}
}
