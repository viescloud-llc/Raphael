package com.vincent.inc.raphael.util;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TTSTextNormalizer {
    
    private static final Map<String, String> ABBREVIATIONS = new HashMap<>();
    private static final Map<String, String> SYMBOLS = new HashMap<>();
    private static final Map<String, String> CURRENCY_SYMBOLS = new HashMap<>();
    private static final Map<String, String> UNITS = new HashMap<>();
    private static final Map<String, String> CONTRACTIONS = new HashMap<>();
    private static final Map<String, String> ROMAN_NUMERALS = new HashMap<>();
    private static final Map<String, String> ORDINALS = new HashMap<>();
    
    static {
        // Common abbreviations and titles
        ABBREVIATIONS.put("Dr\\.", "Doctor");
        ABBREVIATIONS.put("Prof\\.", "Professor");
        ABBREVIATIONS.put("Mr\\.", "Mister");
        ABBREVIATIONS.put("Mrs\\.", "Misses");
        ABBREVIATIONS.put("Ms\\.", "Miss");
        ABBREVIATIONS.put("Jr\\.", "Junior");
        ABBREVIATIONS.put("Sr\\.", "Senior");
        ABBREVIATIONS.put("Ph\\.D\\.", "P H D");
        ABBREVIATIONS.put("M\\.D\\.", "M D");
        ABBREVIATIONS.put("B\\.A\\.", "B A");
        ABBREVIATIONS.put("M\\.A\\.", "M A");
        ABBREVIATIONS.put("B\\.S\\.", "B S");
        ABBREVIATIONS.put("M\\.S\\.", "M S");
        ABBREVIATIONS.put("CEO", "C E O");
        ABBREVIATIONS.put("CFO", "C F O");
        ABBREVIATIONS.put("CTO", "C T O");
        ABBREVIATIONS.put("VP", "V P");
        ABBREVIATIONS.put("Corp\\.", "Corporation");
        ABBREVIATIONS.put("Inc\\.", "Incorporated");
        ABBREVIATIONS.put("Ltd\\.", "Limited");
        ABBREVIATIONS.put("LLC", "L L C");
        ABBREVIATIONS.put("Co\\.", "Company");
        ABBREVIATIONS.put("Dept\\.", "Department");
        ABBREVIATIONS.put("Ave\\.", "Avenue");
        ABBREVIATIONS.put("St\\.", "Street");
        ABBREVIATIONS.put("Rd\\.", "Road");
        ABBREVIATIONS.put("Blvd\\.", "Boulevard");
        ABBREVIATIONS.put("Apt\\.", "Apartment");
        ABBREVIATIONS.put("Ste\\.", "Suite");
        ABBREVIATIONS.put("etc\\.", "et cetera");
        ABBREVIATIONS.put("vs\\.", "versus");
        ABBREVIATIONS.put("e\\.g\\.", "for example");
        ABBREVIATIONS.put("i\\.e\\.", "that is");
        ABBREVIATIONS.put("AM", "A M");
        ABBREVIATIONS.put("PM", "P M");
        ABBREVIATIONS.put("WiFi", "Wi Fi");
        ABBREVIATIONS.put("IoT", "I o T");
        ABBREVIATIONS.put("HTTP", "H T T P");
        ABBREVIATIONS.put("HTTPS", "H T T P S");
        ABBREVIATIONS.put("URL", "U R L");
        ABBREVIATIONS.put("URLs", "U R L s");
        ABBREVIATIONS.put("API", "A P I");
        ABBREVIATIONS.put("CPU", "C P U");
        ABBREVIATIONS.put("GPU", "G P U");
        ABBREVIATIONS.put("RAM", "R A M");
        ABBREVIATIONS.put("SSD", "S S D");
        ABBREVIATIONS.put("HDD", "H D D");
        ABBREVIATIONS.put("USB", "U S B");
        ABBREVIATIONS.put("DVD", "D V D");
        ABBREVIATIONS.put("CD", "C D");
        ABBREVIATIONS.put("TV", "T V");
        ABBREVIATIONS.put("GPS", "G P S");
        ABBREVIATIONS.put("NASA", "N A S A");
        ABBREVIATIONS.put("FBI", "F B I");
        ABBREVIATIONS.put("CIA", "C I A");
        ABBREVIATIONS.put("USA", "U S A");
        ABBREVIATIONS.put("UK", "U K");
        ABBREVIATIONS.put("EU", "E U");
        ABBREVIATIONS.put("UN", "U N");
        ABBREVIATIONS.put("Q1", "Q one");
        ABBREVIATIONS.put("Q2", "Q two");
        ABBREVIATIONS.put("Q3", "Q three");
        ABBREVIATIONS.put("Q4", "Q four");
        ABBREVIATIONS.put("AI", "A I");
        ABBREVIATIONS.put("ML", "M L");
        ABBREVIATIONS.put("AR", "A R");
        ABBREVIATIONS.put("VR", "V R");
        ABBREVIATIONS.put("IT", "I T");
        
        // Symbols
        SYMBOLS.put("&", "and");
        SYMBOLS.put("@", "at");
        SYMBOLS.put("#", "hashtag");
        SYMBOLS.put("%", "percent");
        SYMBOLS.put("\\+", "plus");
        SYMBOLS.put("-", "minus");
        SYMBOLS.put("\\*", "asterisk");
        SYMBOLS.put("/", "slash");
        SYMBOLS.put("\\\\", "backslash");
        SYMBOLS.put("=", "equals");
        SYMBOLS.put("<", "less than");
        SYMBOLS.put(">", "greater than");
        SYMBOLS.put("\\^", "caret");
        SYMBOLS.put("~", "tilde");
        SYMBOLS.put("\\|", "pipe");
        SYMBOLS.put("§", "section");
        SYMBOLS.put("©", "copyright");
        SYMBOLS.put("®", "registered");
        SYMBOLS.put("™", "trademark");
        SYMBOLS.put("†", "dagger");
        SYMBOLS.put("‡", "double dagger");
        SYMBOLS.put("°", "degrees");
        SYMBOLS.put("′", "prime");
        SYMBOLS.put("″", "double prime");
        SYMBOLS.put("∞", "infinity");
        SYMBOLS.put("±", "plus or minus");
        SYMBOLS.put("×", "times");
        SYMBOLS.put("÷", "divided by");
        SYMBOLS.put("√", "square root");
        SYMBOLS.put("≈", "approximately");
        SYMBOLS.put("≠", "not equal");
        SYMBOLS.put("≤", "less than or equal");
        SYMBOLS.put("≥", "greater than or equal");
        SYMBOLS.put("→", "arrow");
        SYMBOLS.put("←", "left arrow");
        SYMBOLS.put("↑", "up arrow");
        SYMBOLS.put("↓", "down arrow");
        
        // Currency symbols
        CURRENCY_SYMBOLS.put("\\$", "dollars");
        CURRENCY_SYMBOLS.put("€", "euros");
        CURRENCY_SYMBOLS.put("£", "pounds");
        CURRENCY_SYMBOLS.put("¥", "yen");
        CURRENCY_SYMBOLS.put("₹", "rupees");
        CURRENCY_SYMBOLS.put("₽", "rubles");
        CURRENCY_SYMBOLS.put("₩", "won");
        CURRENCY_SYMBOLS.put("¢", "cents");
        
        // Units
        UNITS.put("km", "kilometers");
        UNITS.put("mm", "millimeters");
        UNITS.put("cm", "centimeters");
        UNITS.put("m", "meters");
        UNITS.put("ft", "feet");
        UNITS.put("in", "inches");
        UNITS.put("mi", "miles");
        UNITS.put("kg", "kilograms");
        UNITS.put("g", "grams");
        UNITS.put("lb", "pounds");
        UNITS.put("oz", "ounces");
        UNITS.put("L", "liters");
        UNITS.put("ml", "milliliters");
        UNITS.put("gal", "gallons");
        UNITS.put("mph", "miles per hour");
        UNITS.put("kph", "kilometers per hour");
        UNITS.put("Hz", "hertz");
        UNITS.put("MHz", "megahertz");
        UNITS.put("GHz", "gigahertz");
        UNITS.put("KB", "kilobytes");
        UNITS.put("MB", "megabytes");
        UNITS.put("GB", "gigabytes");
        UNITS.put("TB", "terabytes");
        UNITS.put("W", "watts");
        UNITS.put("kW", "kilowatts");
        UNITS.put("V", "volts");
        UNITS.put("A", "amperes");
        UNITS.put("psi", "P S I");
        UNITS.put("rpm", "R P M");
        
        // Contractions
        CONTRACTIONS.put("don't", "do not");
        CONTRACTIONS.put("won't", "will not");
        CONTRACTIONS.put("can't", "cannot");
        CONTRACTIONS.put("shouldn't", "should not");
        CONTRACTIONS.put("wouldn't", "would not");
        CONTRACTIONS.put("couldn't", "could not");
        CONTRACTIONS.put("isn't", "is not");
        CONTRACTIONS.put("aren't", "are not");
        CONTRACTIONS.put("wasn't", "was not");
        CONTRACTIONS.put("weren't", "were not");
        CONTRACTIONS.put("haven't", "have not");
        CONTRACTIONS.put("hasn't", "has not");
        CONTRACTIONS.put("hadn't", "had not");
        CONTRACTIONS.put("didn't", "did not");
        CONTRACTIONS.put("doesn't", "does not");
        CONTRACTIONS.put("I'm", "I am");
        CONTRACTIONS.put("you're", "you are");
        CONTRACTIONS.put("he's", "he is");
        CONTRACTIONS.put("she's", "she is");
        CONTRACTIONS.put("it's", "it is");
        CONTRACTIONS.put("we're", "we are");
        CONTRACTIONS.put("they're", "they are");
        CONTRACTIONS.put("I'll", "I will");
        CONTRACTIONS.put("you'll", "you will");
        CONTRACTIONS.put("he'll", "he will");
        CONTRACTIONS.put("she'll", "she will");
        CONTRACTIONS.put("it'll", "it will");
        CONTRACTIONS.put("we'll", "we will");
        CONTRACTIONS.put("they'll", "they will");
        CONTRACTIONS.put("I've", "I have");
        CONTRACTIONS.put("you've", "you have");
        CONTRACTIONS.put("we've", "we have");
        CONTRACTIONS.put("they've", "they have");
        CONTRACTIONS.put("I'd", "I would");
        CONTRACTIONS.put("you'd", "you would");
        CONTRACTIONS.put("he'd", "he would");
        CONTRACTIONS.put("she'd", "she would");
        CONTRACTIONS.put("we'd", "we would");
        CONTRACTIONS.put("they'd", "they would");
        CONTRACTIONS.put("that's", "that is");
        CONTRACTIONS.put("there's", "there is");
        CONTRACTIONS.put("here's", "here is");
        CONTRACTIONS.put("what's", "what is");
        CONTRACTIONS.put("where's", "where is");
        CONTRACTIONS.put("who's", "who is");
        CONTRACTIONS.put("how's", "how is");
        CONTRACTIONS.put("let's", "let us");
        
        // Roman numerals
        ROMAN_NUMERALS.put("\\bI\\b", "one");
        ROMAN_NUMERALS.put("\\bII\\b", "two");
        ROMAN_NUMERALS.put("\\bIII\\b", "three");
        ROMAN_NUMERALS.put("\\bIV\\b", "four");
        ROMAN_NUMERALS.put("\\bV\\b", "five");
        ROMAN_NUMERALS.put("\\bVI\\b", "six");
        ROMAN_NUMERALS.put("\\bVII\\b", "seven");
        ROMAN_NUMERALS.put("\\bVIII\\b", "eight");
        ROMAN_NUMERALS.put("\\bIX\\b", "nine");
        ROMAN_NUMERALS.put("\\bX\\b", "ten");
        ROMAN_NUMERALS.put("\\bXI\\b", "eleven");
        ROMAN_NUMERALS.put("\\bXII\\b", "twelve");
        ROMAN_NUMERALS.put("\\bXX\\b", "twenty");
        ROMAN_NUMERALS.put("\\bXXI\\b", "twenty one");
        ROMAN_NUMERALS.put("\\bL\\b", "fifty");
        ROMAN_NUMERALS.put("\\bC\\b", "one hundred");
        ROMAN_NUMERALS.put("\\bD\\b", "five hundred");
        ROMAN_NUMERALS.put("\\bM\\b", "one thousand");
        
        // Ordinals mapping
        ORDINALS.put("1st", "first");
        ORDINALS.put("2nd", "second");
        ORDINALS.put("3rd", "third");
        ORDINALS.put("4th", "fourth");
        ORDINALS.put("5th", "fifth");
        ORDINALS.put("6th", "sixth");
        ORDINALS.put("7th", "seventh");
        ORDINALS.put("8th", "eighth");
        ORDINALS.put("9th", "ninth");
        ORDINALS.put("10th", "tenth");
        ORDINALS.put("11th", "eleventh");
        ORDINALS.put("12th", "twelfth");
        ORDINALS.put("13th", "thirteenth");
        ORDINALS.put("20th", "twentieth");
        ORDINALS.put("21st", "twenty first");
        ORDINALS.put("22nd", "twenty second");
        ORDINALS.put("23rd", "twenty third");
        ORDINALS.put("30th", "thirtieth");
        ORDINALS.put("31st", "thirty first");
    }
    
    public static String normalizeForTTS(String text) {
        String normalized = Normalizer.normalize(text, Form.NFKC);
        
        // 1. Handle contractions first (before other punctuation removal)
        normalized = normalizeContractions(normalized);
        
        // 2. Handle URLs and email addresses
        normalized = normalizeUrls(normalized);
        normalized = normalizeEmails(normalized);
        
        // 3. Handle phone numbers
        normalized = normalizePhoneNumbers(normalized);
        
        // 4. Handle IP addresses
        normalized = normalizeIpAddresses(normalized);
        
        // 5. Handle social media handles and hashtags
        normalized = normalizeSocialMedia(normalized);
        
        // 6. Handle file paths and extensions
        normalized = normalizeFilePaths(normalized);
        
        // 7. Handle version numbers
        normalized = normalizeVersionNumbers(normalized);
        
        // 8. Handle fractions
        normalized = normalizeFractions(normalized);
        
        // 9. Handle percentages and ratios
        normalized = normalizePercentagesAndRatios(normalized);
        
        // 10. Handle currency and numbers
        normalized = normalizeCurrency(normalized);
        normalized = normalizeNumbers(normalized);
        
        // 11. Handle dates and times
        normalized = normalizeDates(normalized);
        normalized = normalizeTimes(normalized);
        
        // 12. Handle temperature and measurements
        normalized = normalizeTemperature(normalized);
        normalized = normalizeUnits(normalized);
        
        // 13. Handle coordinates
        normalized = normalizeCoordinates(normalized);
        
        // 14. Handle mathematical expressions
        normalized = normalizeMath(normalized);
        
        // 15. Handle scientific notation
        normalized = normalizeScientificNotation(normalized);
        
        // 16. Handle hexadecimal and technical codes
        normalized = normalizeHexadecimal(normalized);
        normalized = normalizeTechnicalCodes(normalized);
        
        // 17. Handle Roman numerals
        normalized = normalizeRomanNumerals(normalized);
        
        // 18. Handle ordinals
        normalized = normalizeOrdinals(normalized);
        
        // 19. Handle acronyms in all caps
        normalized = normalizeAcronyms(normalized);
        
        // 20. Handle abbreviations
        normalized = normalizeAbbreviations(normalized);
        
        // 21. Handle symbols
        normalized = normalizeSymbols(normalized);
        
        // 22. Handle emoticons and emojis
        // normalized = normalizeEmoticons(normalized);
        
        // 23. Handle special punctuation
        // normalized = normalizeSpecialPunctuation(normalized);
        
        // 24. Clean up formatting
        normalized = cleanupFormatting(normalized);
        
        return normalized;
    }
    
    private static String normalizeContractions(String text) {
        for (Map.Entry<String, String> entry : CONTRACTIONS.entrySet()) {
            text = text.replaceAll("(?i)\\b" + entry.getKey() + "\\b", entry.getValue());
        }
        return text;
    }
    
    private static String normalizeUrls(String text) {
        Pattern urlPattern = Pattern.compile("https?://[\\w\\./\\-_?=&%]+");
        Matcher matcher = urlPattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        
        while (matcher.find()) {
            String url = matcher.group();
            String readable = url.replaceAll("https?://", "")
                               .replaceAll("www\\.", "w w w dot ")
                               .replaceAll("\\.", " dot ")
                               .replaceAll("/", " slash ")
                               .replaceAll("_", " underscore ")
                               .replaceAll("-", " dash ")
                               .replaceAll("\\?", " question mark ")
                               .replaceAll("&", " and ")
                               .replaceAll("=", " equals ");
            matcher.appendReplacement(sb, readable);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    private static String normalizeEmails(String text) {
        Pattern emailPattern = Pattern.compile("[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}");
        Matcher matcher = emailPattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        
        while (matcher.find()) {
            String email = matcher.group();
            String readable = email.replaceAll("@", " at ")
                                  .replaceAll("\\.", " dot ");
            matcher.appendReplacement(sb, readable);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    private static String normalizePhoneNumbers(String text) {
        // Handle various phone number formats
        text = text.replaceAll("\\+(\\d{1,3})\\s?(\\d{3})\\s?(\\d{3})\\s?(\\d{4})", "plus $1 $2 $3 $4");
        text = text.replaceAll("\\((\\d{3})\\)\\s?(\\d{3})-(\\d{4})", "$1 $2 $3");
        text = text.replaceAll("(\\d{3})-(\\d{3})-(\\d{4})", "$1 $2 $3");
        text = text.replaceAll("(\\d{3})\\.?(\\d{3})\\.?(\\d{4})", "$1 $2 $3");
        return text;
    }
    
    private static String normalizeIpAddresses(String text) {
        // Handle IP addresses
        Pattern ipPattern = Pattern.compile("\\b(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\b");
        Matcher matcher = ipPattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        
        while (matcher.find()) {
            String ip = matcher.group(1) + " dot " + matcher.group(2) + " dot " + 
                       matcher.group(3) + " dot " + matcher.group(4);
            matcher.appendReplacement(sb, ip);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    private static String normalizeSocialMedia(String text) {
        // Handle Twitter/X handles and hashtags
        text = text.replaceAll("@(\\w+)", "at $1");
        text = text.replaceAll("#(\\w+)", "hashtag $1");
        return text;
    }
    
    private static String normalizeFilePaths(String text) {
        // Handle file extensions
        text = text.replaceAll("\\.(txt|pdf|doc|docx|xls|xlsx|ppt|pptx|jpg|jpeg|png|gif|mp3|mp4|avi|mov|zip|rar|exe|dll)\\b", " dot $1");
        
        // Handle file paths
        text = text.replaceAll("C:\\\\", "C drive backslash ");
        text = text.replaceAll("/home/", "slash home slash ");
        text = text.replaceAll("/usr/", "slash user slash ");
        
        return text;
    }
    
    private static String normalizeVersionNumbers(String text) {
        // Handle version numbers like v2.1.3 or version 1.0
        text = text.replaceAll("v(\\d+)\\.(\\d+)\\.(\\d+)", "version $1 point $2 point $3");
        text = text.replaceAll("version\\s+(\\d+)\\.(\\d+)", "version $1 point $2");
        return text;
    }
    
    private static String normalizeFractions(String text) {
        // Handle common fractions
        text = text.replaceAll("\\b1/2\\b", "one half");
        text = text.replaceAll("\\b1/3\\b", "one third");
        text = text.replaceAll("\\b2/3\\b", "two thirds");
        text = text.replaceAll("\\b1/4\\b", "one quarter");
        text = text.replaceAll("\\b3/4\\b", "three quarters");
        text = text.replaceAll("\\b1/8\\b", "one eighth");
        text = text.replaceAll("\\b(\\d+)/(\\d+)", "$1 over $2");
        
        // Unicode fractions
        text = text.replaceAll("½", "one half");
        text = text.replaceAll("⅓", "one third");
        text = text.replaceAll("⅔", "two thirds");
        text = text.replaceAll("¼", "one quarter");
        text = text.replaceAll("¾", "three quarters");
        text = text.replaceAll("⅛", "one eighth");
        text = text.replaceAll("⅜", "three eighths");
        text = text.replaceAll("⅝", "five eighths");
        text = text.replaceAll("⅞", "seven eighths");
        
        return text;
    }
    
    private static String normalizePercentagesAndRatios(String text) {
        // Handle percentages
        text = text.replaceAll("(\\d+(?:\\.\\d+)?)%", "$1 percent");
        
        // Handle ratios
        text = text.replaceAll("(\\d+):(\\d+)", "$1 to $2");
        
        return text;
    }
    
    private static String normalizeCurrency(String text) {
        for (Map.Entry<String, String> entry : CURRENCY_SYMBOLS.entrySet()) {
            String symbol = entry.getKey();
            String word = entry.getValue();
            
            // Handle trillions, billions, millions, thousands
            text = text.replaceAll(symbol + "(\\d+(?:\\.\\d+)?)T", "$1 trillion " + word);
            text = text.replaceAll(symbol + "(\\d+(?:\\.\\d+)?)B", "$1 billion " + word);
            text = text.replaceAll(symbol + "(\\d+(?:\\.\\d+)?)M", "$1 million " + word);
            text = text.replaceAll(symbol + "(\\d+(?:\\.\\d+)?)K", "$1 thousand " + word);
            text = text.replaceAll(symbol + "(\\d+(?:\\.\\d+)?)", "$1 " + word);
        }
        return text;
    }
    
    private static String normalizeNumbers(String text) {
        // Handle large numbers with commas
        text = text.replaceAll("(\\d{1,3}),(\\d{3}),(\\d{3}),(\\d{3})", "$1 billion $2 million $3 thousand $4");
        text = text.replaceAll("(\\d{1,3}),(\\d{3}),(\\d{3})", "$1 million $2 thousand $3");
        text = text.replaceAll("(\\d{1,3}),(\\d{3})", "$1 thousand $2");
        
        // Handle decimal numbers
        text = text.replaceAll("(\\d+)\\.(\\d+)", "$1 point $2");
        
        // Handle negative numbers
        text = text.replaceAll("-(\\d+)", "negative $1");
        
        // Handle years in format 2024/25
        text = text.replaceAll("(\\d{4})/(\\d{2})", "$1 slash $2");
        
        // Handle ranges
        text = text.replaceAll("(\\d+)-(\\d+)", "$1 to $2");
        
        // Handle error codes like 404
        text = text.replaceAll("\\b(\\d{3})\\s+error", "error $1");
        
        return text;
    }
    
    private static String normalizeDates(String text) {
        // Handle month/day/year formats
        text = text.replaceAll("(\\d{1,2})/(\\d{1,2})/(\\d{4})", "$1 slash $2 slash $3");
        text = text.replaceAll("(\\d{1,2})/(\\d{1,2})/(\\d{2})", "$1 slash $2 slash $3");
        
        // Handle ISO dates
        text = text.replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$1 dash $2 dash $3");
        
        return text;
    }
    
    private static String normalizeTimes(String text) {
        // Handle time formats
        text = text.replaceAll("(\\d{1,2}):(\\d{2}):(\\d{2})", "$1 $2 $3");
        text = text.replaceAll("(\\d{1,2}):(\\d{2})", "$1 $2");
        
        return text;
    }
    
    private static String normalizeTemperature(String text) {
        text = text.replaceAll("(-?\\d+(?:\\.\\d+)?)°C", "$1 degrees Celsius");
        text = text.replaceAll("(-?\\d+(?:\\.\\d+)?)°F", "$1 degrees Fahrenheit");
        text = text.replaceAll("(-?\\d+(?:\\.\\d+)?)°K", "$1 degrees Kelvin");
        return text;
    }
    
    private static String normalizeUnits(String text) {
        for (Map.Entry<String, String> entry : UNITS.entrySet()) {
            text = text.replaceAll("(\\d+(?:\\.\\d+)?)\\s?" + entry.getKey() + "\\b", "$1 " + entry.getValue());
        }
        return text;
    }
    
    private static String normalizeCoordinates(String text) {
        // Handle GPS coordinates
        text = text.replaceAll("(\\d+(?:\\.\\d+)?)°(\\d+)'(\\d+(?:\\.\\d+)?)\"([NS])", "$1 degrees $2 minutes $3 seconds $4");
        text = text.replaceAll("(\\d+(?:\\.\\d+)?)°([NS])", "$1 degrees $2");
        return text;
    }
    
    private static String normalizeMath(String text) {
        // Handle mathematical expressions
        text = text.replaceAll("\\b(\\d+)\\^(\\d+)", "$1 to the power of $2");
        text = text.replaceAll("\\b(\\d+)²", "$1 squared");
        text = text.replaceAll("\\b(\\d+)³", "$1 cubed");
        text = text.replaceAll("log\\((\\d+)\\)", "log of $1");
        text = text.replaceAll("sin\\((\\d+)\\)", "sine of $1");
        text = text.replaceAll("cos\\((\\d+)\\)", "cosine of $1");
        text = text.replaceAll("tan\\((\\d+)\\)", "tangent of $1");
        return text;
    }
    
    private static String normalizeScientificNotation(String text) {
        // Handle scientific notation like 1.5e10 or 2.3E-5
        text = text.replaceAll("(\\d+(?:\\.\\d+)?)e([+-]?\\d+)", "$1 times 10 to the $2");
        text = text.replaceAll("(\\d+(?:\\.\\d+)?)E([+-]?\\d+)", "$1 times 10 to the $2");
        return text;
    }
    
    private static String normalizeHexadecimal(String text) {
        // Handle hexadecimal codes
        text = text.replaceAll("0x([0-9A-Fa-f]+)", "hex $1");
        text = text.replaceAll("#([0-9A-Fa-f]{6})", "hex color $1");
        text = text.replaceAll("#([0-9A-Fa-f]{3})", "hex color $1");
        return text;
    }
    
    private static String normalizeTechnicalCodes(String text) {
        text = text.replaceAll("ERROR_CODE", "error code");
        text = text.replaceAll("HTTP_", "H T T P underscore ");
        text = text.replaceAll("API_", "A P I underscore ");
        text = text.replaceAll("_", " underscore ");
        return text;
    }
    
    private static String normalizeRomanNumerals(String text) {
        for (Map.Entry<String, String> entry : ROMAN_NUMERALS.entrySet()) {
            text = text.replaceAll(entry.getKey(), entry.getValue());
        }
        return text;
    }
    
    private static String normalizeOrdinals(String text) {
        for (Map.Entry<String, String> entry : ORDINALS.entrySet()) {
            text = text.replaceAll("\\b" + entry.getKey() + "\\b", entry.getValue());
        }
        
        // Handle generic ordinals
        text = text.replaceAll("(\\d+)st", "$1st");
        text = text.replaceAll("(\\d+)nd", "$1nd");
        text = text.replaceAll("(\\d+)rd", "$1rd");
        text = text.replaceAll("(\\d+)th", "$1th");
        
        return text;
    }
    
    private static String normalizeAcronyms(String text) {
        // Handle acronyms in all caps (3+ letters) by spacing them out
        Pattern acronymPattern = Pattern.compile("\\b([A-Z]{3,})\\b");
        Matcher matcher = acronymPattern.matcher(text);
        StringBuffer sb = new StringBuffer();
        
        while (matcher.find()) {
            String acronym = matcher.group(1);
            // Skip if it's already in our abbreviations map
            boolean inAbbreviations = ABBREVIATIONS.containsKey(acronym) || 
                                    ABBREVIATIONS.containsKey(acronym + "\\.");
            
            if (!inAbbreviations) {
                // Space out the letters
                String spaced = acronym.replaceAll("(.)", "$1 ").trim();
                matcher.appendReplacement(sb, spaced);
            } else {
                matcher.appendReplacement(sb, acronym);
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String normalizeAbbreviations(String text) {
        for (Map.Entry<String, String> entry : ABBREVIATIONS.entrySet()) {
            text = text.replaceAll("\\b" + entry.getKey() + "\\b", entry.getValue());
        }
        return text;
    }
    
    private static String normalizeSymbols(String text) {
        for (Map.Entry<String, String> entry : SYMBOLS.entrySet()) {
            text = text.replaceAll(entry.getKey(), " " + entry.getValue() + " ");
        }
        return text;
    }
    
    private static String cleanupFormatting(String text) {
        // Remove or replace problematic punctuation for TTS
        text = text.replaceAll("'", " ");  // Remove apostrophes
        text = text.replaceAll("\"", " ");  // Remove quotes
        text = text.replaceAll("\\(", " ");  // Remove parentheses
        text = text.replaceAll("\\)", " ");
        text = text.replaceAll("\\[", " ");  // Remove brackets
        text = text.replaceAll("\\]", " ");
        text = text.replaceAll("—", " ");   // Replace em dash
        text = text.replaceAll("–", " ");   // Replace en dash
        
        // Clean up multiple spaces
        text = text.replaceAll("\\s+", " ");
        text = text.trim();
        
        return text;
    }

}