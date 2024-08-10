package com.example.islami.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.islami.R
import androidx.compose.ui.text.font.Font

val naskh = FontFamily(
    Font(R.font.naskh_uthman_tn),
    Font(R.font.naskh_uthman_tnb)
)

val thulth = FontFamily(
   Font(R.font.thulth)
)

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
)



val providerAmiri = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamilyAmiri = FontFamily(
    Font(
        googleFont = GoogleFont("Amiri"),
        fontProvider = providerAmiri,
    )
)

val displayFontFamilyAmiri = FontFamily(
    Font(
        googleFont = GoogleFont("Amiri"),
        fontProvider = providerAmiri,
    )
)

// Default Material 3 typography values
val baselineAmiri = Typography()

val AppTypographyAmiri = Typography(
    displayLarge = baselineAmiri.displayLarge.copy(fontFamily =displayFontFamilyAmiri),
    displayMedium = baselineAmiri.displayMedium.copy(fontFamily = displayFontFamilyAmiri),
    displaySmall = baselineAmiri.displaySmall.copy(fontFamily = displayFontFamilyAmiri),
    headlineLarge = baselineAmiri.headlineLarge.copy(fontFamily = displayFontFamilyAmiri),
    headlineMedium = baselineAmiri.headlineMedium.copy(fontFamily = displayFontFamilyAmiri),
    headlineSmall = baselineAmiri.headlineSmall.copy(fontFamily = displayFontFamilyAmiri),
    titleLarge = baselineAmiri.titleLarge.copy(fontFamily = displayFontFamilyAmiri),
    titleMedium = baselineAmiri.titleMedium.copy(fontFamily = displayFontFamilyAmiri),
    titleSmall =baselineAmiri.titleSmall.copy(fontFamily = displayFontFamilyAmiri),
    bodyLarge = baselineAmiri.bodyLarge.copy(fontFamily = bodyFontFamilyAmiri),
    bodyMedium = baselineAmiri.bodyMedium.copy(fontFamily = bodyFontFamilyAmiri),
    bodySmall = baselineAmiri.bodySmall.copy(fontFamily = bodyFontFamilyAmiri),
    labelLarge = baselineAmiri.labelLarge.copy(fontFamily = bodyFontFamilyAmiri),
    labelMedium = baselineAmiri.labelMedium.copy(fontFamily = bodyFontFamilyAmiri),
    labelSmall = baselineAmiri.labelSmall.copy(fontFamily = bodyFontFamilyAmiri),
)