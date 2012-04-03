KSearch 2
=========

KSearch 2 is a Solr Korean language analyzer.

Dependency
----------

 * Solr 3.5.0
 * Lucene 3.5.0
 * Maven (To build this project)

Building
--------

 1. mvn package

Usage
-----

1. Build this project.
2. Put target/ksearch2-1.0-SNAPSHOT.jar to your solr lib path.
3. Setup schema.xml to use KoreanStemFilterFactory.

Example
-------

KSearch configuration example using solr example.

1. Download & extract lucene-solr 3.5.0 to ~/lucene-solr
2. Download KSearch 2 to ~/ksearch2
3. cd ~/ksearch2
4. mvn package
5. mkdir -p ~/lucene-solr/solr/example/solr/lib
6. Add "<lib dir="./lib" />" to ~/lucene-solr/solr/example/solr/conf/solrconfig.xml
7. cp ~/ksearch2/target/ksearch2-1.0-SNAPSHOT.jar ~/lucene-solr/solr/example/solr/lib
8. Edit ~/lucene-solr/solr/example/solr/conf/schema.xml accordingly:

    <fieldType name="text_general" class="solr.TextField" positionIncrementGap="100">
      <analyzer type="index">
        <tokenizer class="solr.StandardTokenizerFactory"/>
        <filter class="com.weclay.ksearch2.KoreanStemFilterFactory" />
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" enablePositionIncrements="true" />
        <!-- in this example, we will only use synonyms at query time
        <filter class="solr.SynonymFilterFactory" synonyms="index_synonyms.txt" ignoreCase="true" expand="false"/>
        -->
        <filter class="solr.LowerCaseFilterFactory"/>
      </analyzer>analyzer>
      <analyzer type="query">
        <tokenizer class="solr.StandardTokenizerFactory"/>
        <filter class="com.weclay.ksearch2.KoreanStemFilterFactory" />
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" enablePositionIncrements="true" />
        <filter class="solr.SynonymFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/>
        <filter class="solr.LowerCaseFilterFactory"/>
      </analyzer>analyzer>
    </fieldType>fieldType>

9. cd ~/lucene-solr/solr/example
10. java -Xmx1024m -jar start.jar
11. Visit http://localhost:8983/solr/admin/analysis.jsp
12. Put "text" in field name.
13. Put "한글 분석이 제대로 될까요?" in Field value.
14. Following should appear:

    한글 분석이 제대로 될까요
    한글 분석 제대로 되
    한글 분석 제대로 되
    한글 분석 제대로 되

15. Your analysis is working properly. Enjoy :)
