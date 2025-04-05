fun main() {
    testsIsValidIPv4()
}


fun isValidIPv4(ip: String): Boolean {

    val parts = ip.split('.')
    if (parts.size != 4) return false

    for (part in parts) {
        if (part.isEmpty() || !part.all { it.isDigit() }) return false

        if (part.length > 1 && part.startsWith('0')) return false

        val num = part.toIntOrNull() ?: return false
        if (num !in 0..255) return false
    }

    return true
}


fun testsIsValidIPv4() {
        // Valid IPv4 addresses
        check("192.168.1.1 is valid", isValidIPv4("192.168.1.1"), true)
        check("0.0.0.0 is valid", isValidIPv4("0.0.0.0"), true)
        check("255.255.255.255 is valid", isValidIPv4("255.255.255.255"), true)
        check("10.0.0.1 is valid", isValidIPv4("10.0.0.1"), true)
        check("127.0.0.1 is valid", isValidIPv4("127.0.0.1"), true)

        // Invalid IPv4 addresses
        check("192.168.1 must have 4 segments", isValidIPv4("192.168.1"), false)
        check("192.168.1.1.1 must have 4 segments", isValidIPv4("192.168.1.1.1"), false)
        check("Empty IP is invalid", isValidIPv4(""), false)
        check("256.168.1.1 exceeds valid range", isValidIPv4("256.168.1.1"), false)
        check("192.168.1.999 exceeds valid range", isValidIPv4("192.168.1.999"), false)
        check("-1.168.1.1 cannot have negative values", isValidIPv4("-1.168.1.1"), false)
        check("01.168.1.1 cannot have leading zeros", isValidIPv4("01.168.1.1"), false)
        check("192.168.01.1 cannot have leading zeros", isValidIPv4("192.168.01.1"), false)
        check("192.168.1.01 cannot have leading zeros", isValidIPv4("192.168.1.01"), false)
        check("a.168.1.1 cannot have non-digits", isValidIPv4("a.168.1.1"), false)
        check("192.168.1.a cannot have non-digits", isValidIPv4("192.168.1.a"), false)
        check(" 192.168.1.1 cannot have leading spaces", isValidIPv4(" 192.168.1.1"), false)
        check("192.168.1.1  cannot have trailing spaces", isValidIPv4("192.168.1.1 "), false)
        check("192..168.1.1 cannot have empty segments", isValidIPv4("192..168.1.1"), false)
    }



fun check(name: String, result: Boolean, correctResult: Boolean) {
    if (result == correctResult) {
        println("Success - $name")
    } else {
        println("Failed - $name")

    }
}