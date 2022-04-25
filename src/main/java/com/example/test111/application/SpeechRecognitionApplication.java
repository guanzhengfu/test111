//package com.example.test111.application;
//
//import static com.google.common.collect.Lists.newArrayList;
//import static com.google.common.collect.Maps.newHashMap;
//import static com.google.common.collect.Sets.newHashSet;
//
//import com.google.cloud.speech.v1.RecognitionAudio;
//import com.google.cloud.speech.v1.RecognitionConfig;
//import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
//import com.google.cloud.speech.v1.SpeechClient;
//import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
//import com.google.cloud.speech.v1.SpeechRecognitionResult;
//import com.google.protobuf.ByteString;
//import com.xx.xx.xx.dto.response.ScoreResultResponse;
//import com.xx.xx.xx.dto.response.SpeechRecognizeResponse;
//import com.xx.xx.xx.backend.dto.response.embedded.DiffResult;
//import com.xx.xx.xx.backend.dto.response.embedded.Word;
//import com.xx.xx.xx.backend.util.diff_match_patch;
//import com.xx.xx.xx.backend.util.diff_match_patch.Diff;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Set;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections4.ListUtils;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
//import org.apache.xmlbeans.impl.common.Levenshtein;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class SpeechRecognitionApplication {
//
//  private final Analyzer standardParticiple = new StandardAnalyzer();
//  private static final Map<String, String> LANGUAGE_CODE_MAP = new HashMap<>();
//  private final diff_match_patch diffMatchPatch;
//  private static final String JAPANESE = "Japanese";
//  private static final String CHINESE = "Chinese";
//  private static final String KOREAN = "Korean";
//  private static final String LESS_COMMONLY_USED_CHINESE_CHARACTERS = "嗲";
//  private static final String LESS_COMMONLY_USED_KOREAN_CHARACTERS = "를";
//  private static final String LESS_COMMONLY_USED_JAPANESE_CHARACTERS = "痩";
//  private static final String LESS_COMMONLY_USED_EUROPEAN_CHARACTERS = "ê";
//  private static final List<String> PUNCTUATION_MARKS = Arrays.asList(",", "-", ".",
//      "，", "。", "!", "！", "?", "？", "¿", "¿", "　", "、");
//  private static final List<String> PUNCTUATION_MARKS_AND_SPACE = ListUtils
//      .union(PUNCTUATION_MARKS, newArrayList(" ", "’", " ", "\t"));
//
//  static {
//    LANGUAGE_CODE_MAP.put("es-MX", "Spanish");
//    LANGUAGE_CODE_MAP.put("fr-FR", "French");
//    LANGUAGE_CODE_MAP.put("ja-JP", JAPANESE);
//    LANGUAGE_CODE_MAP.put("it-IT", "Italian");
//    LANGUAGE_CODE_MAP.put("de-DE", "German");
//    LANGUAGE_CODE_MAP.put("ru-RU", "Russian");
//    LANGUAGE_CODE_MAP.put("zh", CHINESE);
//    LANGUAGE_CODE_MAP.put("ko-KR", KOREAN);
//    LANGUAGE_CODE_MAP.put("pt-BR", "Portuguese");
//    LANGUAGE_CODE_MAP.put("es-ES", "Spanish (Spain-Castilian)");
//    LANGUAGE_CODE_MAP.put("iw-IL", "Hebrew");
//    LANGUAGE_CODE_MAP.put("no-NO", "Norwegian");
//    LANGUAGE_CODE_MAP.put("en-US", "English");
//  }
//
//  public ScoreResultResponse speechRecognizeAndScore(MultipartFile file, String languageCode,
//      String originalText, AudioEncoding audioEncoding, int sampleRateHertz)
//      throws IOException {
//    SpeechRecognizeResponse speechRecognizeResponse = speechRecognize(file, languageCode,
//        audioEncoding, sampleRateHertz);
//    return diffAndScore(speechRecognizeResponse.getTranscript(), originalText,
//        speechRecognizeResponse.getLanguage(), speechRecognizeResponse.getConfidence());
//  }
//
//  private SpeechRecognizeResponse speechRecognize(MultipartFile file, String languageCode,
//      AudioEncoding audioEncoding, int sampleRateHertz)
//      throws IOException {
//    RecognitionConfig config = RecognitionConfig.newBuilder()
//        .setEncoding(audioEncoding)
//        .setSampleRateHertz(sampleRateHertz)
//        .setLanguageCode(languageCode)
//        .build();
//    RecognitionAudio audio = RecognitionAudio.newBuilder()
//        .setContent(ByteString.copyFrom(file.getBytes()))
//        .build();
//    List<SpeechRecognitionResult> results = speechClient.recognize(config, audio).getResultsList();
//    StringBuilder transcript = new StringBuilder();
//    float confidence = 0;
//    for (SpeechRecognitionResult result : results) {
//      SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
//      confidence += alternative.getConfidence();
//      transcript.append(alternative.getTranscript());
//    }
//    int resultSize = results.size();
//    if (resultSize > 0) {
//      confidence /= resultSize;
//    }
//    return SpeechRecognizeResponse.builder()
//        .confidence(confidence)
//        .transcript(transcript.toString())
//        .language(LANGUAGE_CODE_MAP.get(languageCode))
//        .build();
//  }
//
//  public ScoreResultResponse diffAndScore(String inputText, String originalText, String language,
//      float confidence)
//      throws IOException {
//    confidence = (float) (Math.round(confidence * 1000)) / 1000;
//    ScoreResultResponse scoreResultResponse = ScoreResultResponse.builder()
//        .inputText(inputText)
//        .originaText(originalText)
//        .confidence(confidence)
//        .language(language).build();
//    List<String> alignWordList = new ArrayList<>();
//    if (StringUtils.isEmpty(inputText) || StringUtils.isEmpty(originalText)) {
//      return scoreResultResponse;
//    }
//    inputText = inputText.replace("’", "'").toLowerCase(Locale.ROOT);
//    originalText = originalText.replace("’", "'").toLowerCase(Locale.ROOT);
//    List<DiffResult> diffResults = getDiffResultsAndDealAlignWords(alignWordList, inputText,
//        originalText, language);
//    scoreResultResponse.setDiffResult(diffResults);
//    return dealStandardParticipleLanguages(String.join("", alignWordList), scoreResultResponse,
//        inputText, originalText, confidence, language);
//  }
//
//  private ScoreResultResponse dealStandardParticipleLanguages(String alignText,
//      ScoreResultResponse scoreResultResponse, String inputText,
//      String originalText, float confidence, String language) throws IOException {
//    List<String> alignWords = dealStandardParticiple(alignText);
//    List<String> inputWords = dealStandardParticiple(inputText);
//    List<String> originalWords = dealStandardParticiple(originalText);
//    List<Word> wordScoreList = new ArrayList<>();
//    String textMatchScore = calculateTextMatchScore(inputText, originalText, confidence);
//    wordScoreList.add(new Word("textMatchScore", -1, textMatchScore));
//    wordScoreList.addAll(
//        dealWordMatchScore(alignWords, inputWords, originalWords, confidence, language,
//            textMatchScore));
//    scoreResultResponse.setScores(wordScoreList);
//    List<DiffResult> wordColor = getWordColor(wordScoreList, confidence,
//        scoreResultResponse.getOriginaText());
//    scoreResultResponse.setWordsColor(wordColor);
//    scoreResultResponse.setErrorWords(getErrorWords(wordScoreList, confidence));
//    scoreResultResponse.setFewerWords(getFewerWords(wordScoreList, confidence));
//    scoreResultResponse.setReadMoreWords(dealReadMoreWords(inputWords, originalWords));
//    return scoreResultResponse;
//  }
//
//  private List<String> dealStandardParticiple(String text)
//      throws IOException {
//    TokenStream textStream = standardParticiple.tokenStream("content", text);
//    textStream.reset();
//    List<String> words = new ArrayList<>();
//    CharTermAttribute charTermAttribute = textStream.getAttribute(CharTermAttribute.class);
//    while (textStream.incrementToken()) {
//      words.add(charTermAttribute.toString());
//    }
//    textStream.end();
//    textStream.close();
//    return words;
//  }
//
//  private List<Word> dealWordMatchScore(List<String> alignWords,
//      List<String> inputWords, List<String> originalWords,
//      float confidence, String language, String textMatchScore) {
//    List<Word> wordScoreList = newArrayList();
//    if (CollectionUtils.isEmpty(inputWords)) {
//      wordScoreList.add(new Word("compositeScore", 0, "0"));
//      return wordScoreList;
//    }
//    int maxWordCount = Math.max(inputWords.size(), originalWords.size());
//    double allScore = 0;
//    int distance;
//    for (int i = 0; i < originalWords.size(); i++) {
//      double bestSimilarWordScore = 0;
//      String originalWord = originalWords.get(i);
//      int bestInputWordPosition = Integer.MAX_VALUE;
//      for (int j = 0; j < inputWords.size(); j++) {
//        String inputWord = inputWords.get(j);
//        double similarWordScore = 1 - (double) Levenshtein.distance(originalWord, inputWord)
//            / Math.max(originalWord.length(), inputWord.length());
//        if (similarWordScore > bestSimilarWordScore || (similarWordScore == bestSimilarWordScore
//            && Math.abs(i - bestInputWordPosition) > Math.abs(i - j))) {
//          bestSimilarWordScore = similarWordScore;
//          bestInputWordPosition = j;
//        }
//      }
//      int wordMaxLength = Math
//          .max(originalWord.length(), inputWords.get(bestInputWordPosition).length());
//      distance = Levenshtein
//          .distance(originalWord, appendAlignWord(wordMaxLength, language, alignWords.get(i)));
//      double score = (1 - (double) distance / wordMaxLength) * confidence;
//      wordScoreList.add(new Word(originalWord, i + 1, String.format("%.3f", score)));
//      allScore += score;
//    }
//    String compositeScore = calculateCompositeScore(allScore, maxWordCount,
//        textMatchScore);
//    wordScoreList.add(new Word("compositeScore", 0, compositeScore));
//    return wordScoreList;
//  }
//
//  private Set<Word> getErrorWords(List<Word> wordScoreList, float confidence) {
//    List<Word> wordList = wordScoreList.subList(1, wordScoreList.size() - 1);
//    Set<Word> errorWords = newHashSet();
//    for (Word word : wordList) {
//      float score = Float.parseFloat(word.getScore());
//      if (score < 1 * confidence && score >= 0.5 * confidence) {
//        errorWords.add(word);
//      }
//    }
//    return errorWords;
//  }
//
//  private Set<Word> getFewerWords(List<Word> wordScoreList, float confidence) {
//    List<Word> wordList = wordScoreList.subList(1, wordScoreList.size() - 1);
//    Set<Word> fewerWords = newHashSet();
//    for (Word word : wordList) {
//      float score = Float.parseFloat(word.getScore());
//      if (score < 0.5 * confidence) {
//        fewerWords.add(word);
//      }
//    }
//    return fewerWords;
//  }
//
//  public String appendAlignWord(int wordMaxLength, String language, String alignWord) {
//    StringBuilder alignWordBuilder = new StringBuilder(alignWord);
//    while (alignWordBuilder.length() < wordMaxLength) {
//      alignWordBuilder.append(getAlignCharacter(language));
//    }
//    return alignWordBuilder.toString();
//  }
//
//  private String calculateTextMatchScore(String inputText, String originalText, float confidence) {
//    String replaceSymbolsRegex = "[" + String.join("", PUNCTUATION_MARKS) + "]";
//    originalText = originalText.replaceAll(replaceSymbolsRegex, "");
//    inputText = inputText.replaceAll(replaceSymbolsRegex, "");
//    int distance = Levenshtein.distance(inputText, originalText);
//    double textMatchScore = (1 - (double) distance / Math.max(inputText.length(),
//        originalText.length())) * confidence;
//    return String.format("%.3f", textMatchScore);
//  }
//
//  private String calculateCompositeScore(double allScore, int maxWordCount, String textMatchScore) {
//    double compositeScore =
//        Double.parseDouble(textMatchScore) * 0.6 + allScore / maxWordCount * 0.4;
//    return String.format("%.3f", compositeScore);
//  }
//
//  private Set<String> dealReadMoreWords(List<String> inputWords, List<String> originalWords) {
//    Map<String, Integer> inputWordCountMap = newHashMap();
//    Map<String, Integer> originalWordCountMap = newHashMap();
//    inputWords.forEach(
//        inputWord -> inputWordCountMap
//            .put(inputWord, inputWordCountMap.getOrDefault(inputWord, 0) + 1)
//    );
//    originalWords.forEach(
//        originalWord -> originalWordCountMap
//            .put(originalWord, originalWordCountMap.getOrDefault(originalWord, 0) + 1)
//    );
//    Set<String> readMoreWords = newHashSet();
//    originalWords.forEach(originalWord -> {
//      if (inputWordCountMap.getOrDefault(originalWord, 0) > originalWordCountMap
//          .getOrDefault(originalWord, 0)) {
//        readMoreWords.add(originalWord);
//      }
//    });
//    return readMoreWords;
//  }
//
//  private List<DiffResult> getDiffResultsAndDealAlignWords(List<String> alignWordList,
//      String inputText,
//      String originalText, String language) {
//    List<Diff> diffs = diffMatchPatch.diff_main(inputText, originalText);
//    List<DiffResult> diffResults = new ArrayList<>();
//    for (Diff diff : diffs) {
//      if ("INSERT".equals(diff.operation.toString())) {
//        diffResults.add(new DiffResult("MissingCharacters", diff.text));
//        alignWordList.add(dealMissCharacters(language, diff.text));
//      } else if ("EQUAL".equals(diff.operation.toString())) {
//        diffResults.add(new DiffResult("EqualCharacters", diff.text));
//        alignWordList.add(diff.text);
//      } else {
//        diffResults.add(new DiffResult("ReadMoreCharacters", diff.text));
//      }
//    }
//    return diffResults;
//  }
//
//  private String dealMissCharacters(String language, String missingText) {
//    StringBuilder stringBuilder = new StringBuilder();
//    for (int i = 0; i < missingText.length(); i++) {
//      if (PUNCTUATION_MARKS_AND_SPACE.contains(String.valueOf(missingText.charAt(i)))) {
//        stringBuilder.append(" ");
//      } else {
//        stringBuilder.append(getAlignCharacter(language));
//      }
//    }
//    return stringBuilder.toString();
//  }
//
//
//  private String getAlignCharacter(String language) {
//    switch (language) {
//      case CHINESE:
//        return LESS_COMMONLY_USED_CHINESE_CHARACTERS;
//      case KOREAN:
//        return LESS_COMMONLY_USED_KOREAN_CHARACTERS;
//      case JAPANESE:
//        return LESS_COMMONLY_USED_JAPANESE_CHARACTERS;
//      default:
//        return LESS_COMMONLY_USED_EUROPEAN_CHARACTERS;
//    }
//  }
//
//  private List<DiffResult> getWordColor(List<Word> words, float confidence, String originalText) {
//    List<Word> wordList = words.subList(1, words.size() - 1);
//    if (wordList.isEmpty()) {
//      return new ArrayList<>();
//    }
//    String green = String.format("%.3f", 1 * confidence);
//    String red = String.format("%.3f", 0.5 * confidence);
//    char[] originalChars = originalText.toCharArray();
//    int scoreIndex = 0;
//    List<DiffResult> wordsColor = new ArrayList<>();
//    for (int i = 0; i < originalChars.length; i++) {
//      if (PUNCTUATION_MARKS_AND_SPACE.contains(String.valueOf(originalChars[i]))) {
//        wordsColor.add(new DiffResult("NoColor", String.valueOf(originalChars[i])));
//      } else {
//        Word word = wordList.get(scoreIndex);
//        scoreIndex++;
//        int length = word.getText().length();
//        String substring = originalText.substring(i, i + length);
//        if (Double.parseDouble(word.getScore()) == Double.parseDouble(green)) {
//          wordsColor.add(new DiffResult("green", substring));
//        } else if (Double.parseDouble(word.getScore()) < Double.parseDouble(red)) {
//          wordsColor.add(new DiffResult("red", substring));
//        } else {
//          wordsColor.add(new DiffResult("blue", substring));
//        }
//        i = i + length - 1;
//      }
//    }
//    return wordsColor;
//  }
//
//}
