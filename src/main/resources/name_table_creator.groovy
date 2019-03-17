import java.text.SimpleDateFormat

def TEMPLATE_DATE = "yyyyMMddHHmm"
def format = new SimpleDateFormat(TEMPLATE_DATE)
def textDate = format.format(new Date())

def userName = System.getProperty("user.name")

def nameTable = """T_${userName.toUpperCase()}_${textDate}"""
nameTable