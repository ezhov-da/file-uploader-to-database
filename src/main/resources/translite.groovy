//Убираем все символы кроме русского, английского, 
//пробела, и нижнего подчеркивания 

def MAP_TRANSLIT = 
[
    "А":"A", "Б":"B", "В":"V",
    "Г":"G", "Д":"D", "Е":"E",
    "Ж":"J", "З":"Z", "И":"I",
    "Й":"Y", "К":"K", "Л":"L",
    "М":"M", "Н":"N", "О":"O",
    "П":"P", "Р":"R", "С":"S",
    "Т":"T", "У":"U", "Ф":"F",
    "Х":"H", "Ц":"TS","Ч":"CH",
    "Ш":"SH","Щ":"SCH","Ъ":"",
    "Ы":"YI", "Ь":"", "Э":"E",
    "Ю":"YU", "Я":"YA", "а":"a",
    "б":"b", "в":"v", "г":"g",
    "д":"d", "е":"e", "ж":"j",
    "з":"z", "и":"i", "й":"y",
    "к":"k", "л":"l", "м":"m",
    "н":"n", "о":"o", "п":"p",
    "р":"r", "с":"s", "т":"t", 
    "у":"u", "ф":"f", "х":"h",
    "ц":"ts", "ч":"ch", "ш":"sh",
    "щ":"sch", "ъ":"y", "ы":"yi",
    "ь":"", "э":"e", "ю":"yu",
    "я":"ya", " ":"_" 
]

//println MAP_TRANSLIT

def REGEXP_PATTERN = /[^А-Яа-яA-Za-z _]/
//def inputWord = "привет_<> how № ltkf -"

//Убираем лишние символы
def wordAfterCleaner = inputWord.replaceAll(REGEXP_PATTERN, '')
//println wordAfterCleaner

//убираем пробелы
wordAfterCleaner = wordAfterCleaner.trim()

MAP_TRANSLIT.each{key, val ->
    wordAfterCleaner = wordAfterCleaner.replaceAll(key, val)
}

wordAfterCleaner = wordAfterCleaner.toUpperCase()

if (wordAfterCleaner == "") {"EMPTY"} else {wordAfterCleaner}
//println wordAfterCleaner