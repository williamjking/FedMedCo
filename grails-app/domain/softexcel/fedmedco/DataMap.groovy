package softexcel.fedmedco

class DataMap {

    static Map countryCodeMapping = ["BD": "BGD", "BE": "BEL", "BF": "BFA", "BG": "BGR", "BA": "BIH", "BB": "BRB",
                                     "WF": "WLF", "BL": "BLM", "BM": "BMU", "BN": "BRN", "BO": "BOL", "BH": "BHR",
                                     "BI": "BDI", "BJ": "BEN", "BT": "BTN", "JM": "JAM", "BV": "BVT", "BW": "BWA",
                                     "WS": "WSM", "BQ": "BES", "BR": "BRA", "BS": "BHS", "JE": "JEY", "BY": "BLR",
                                     "BZ": "BLZ", "RU": "RUS", "RW": "RWA", "RS": "SRB", "TL": "TLS", "RE": "REU",
                                     "TM": "TKM", "TJ": "TJK", "RO": "ROU", "TK": "TKL", "GW": "GNB", "GU": "GUM",
                                     "GT": "GTM", "GS": "SGS", "GR": "GRC", "GQ": "GNQ", "GP": "GLP", "JP": "JPN",
                                     "GY": "GUY", "GG": "GGY", "GF": "GUF", "GE": "GEO", "GD": "GRD", "GB": "GBR",
                                     "GA": "GAB", "SV": "SLV", "GN": "GIN", "GM": "GMB", "GL": "GRL", "GI": "GIB",
                                     "GH": "GHA", "OM": "OMN", "TN": "TUN", "JO": "JOR", "HR": "HRV", "HT": "HTI",
                                     "HU": "HUN", "HK": "HKG", "HN": "HND", "HM": "HMD", "VE": "VEN", "PR": "PRI",
                                     "PS": "PSE", "PW": "PLW", "PT": "PRT", "SJ": "SJM", "PY": "PRY", "IQ": "IRQ",
                                     "PA": "PAN", "PF": "PYF", "PG": "PNG", "PE": "PER", "PK": "PAK", "PH": "PHL",
                                     "PN": "PCN", "PL": "POL", "PM": "SPM", "ZM": "ZMB", "EH": "ESH", "EE": "EST",
                                     "EG": "EGY", "ZA": "ZAF", "EC": "ECU", "IT": "ITA", "VN": "VNM", "SB": "SLB",
                                     "ET": "ETH", "SO": "SOM", "ZW": "ZWE", "SA": "SAU", "ES": "ESP", "ER": "ERI",
                                     "ME": "MNE", "MD": "MDA", "MG": "MDG", "MF": "MAF", "MA": "MAR", "MC": "MCO",
                                     "UZ": "UZB", "MM": "MMR", "ML": "MLI", "MO": "MAC", "MN": "MNG", "MH": "MHL",
                                     "MK": "MKD", "MU": "MUS", "MT": "MLT", "MW": "MWI", "MV": "MDV", "MQ": "MTQ",
                                     "MP": "MNP", "MS": "MSR", "MR": "MRT", "IM": "IMN", "UG": "UGA", "TZ": "TZA",
                                     "MY": "MYS", "MX": "MEX", "IL": "ISR", "FR": "FRA", "IO": "IOT", "SH": "SHN",
                                     "FI": "FIN", "FJ": "FJI", "FK": "FLK", "FM": "FSM", "FO": "FRO", "NI": "NIC",
                                     "NL": "NLD", "NO": "NOR", "NA": "NAM", "VU": "VUT", "NC": "NCL", "NE": "NER",
                                     "NF": "NFK", "NG": "NGA", "NZ": "NZL", "NP": "NPL", "NR": "NRU", "NU": "NIU",
                                     "CK": "COK", "XK": "XKX", "CI": "CIV", "CH": "CHE", "CO": "COL", "CN": "CHN",
                                     "CM": "CMR", "CL": "CHL", "CC": "CCK", "CA": "CAN", "CG": "COG", "CF": "CAF",
                                     "CD": "COD", "CZ": "CZE", "CY": "CYP", "CX": "CXR", "CR": "CRI", "CW": "CUW",
                                     "CV": "CPV", "CU": "CUB", "SZ": "SWZ", "SY": "SYR", "SX": "SXM", "KG": "KGZ",
                                     "KE": "KEN", "SS": "SSD", "SR": "SUR", "KI": "KIR", "KH": "KHM", "KN": "KNA",
                                     "KM": "COM", "ST": "STP", "SK": "SVK", "KR": "KOR", "SI": "SVN", "KP": "PRK",
                                     "KW": "KWT", "SN": "SEN", "SM": "SMR", "SL": "SLE", "SC": "SYC", "KZ": "KAZ",
                                     "KY": "CYM", "SG": "SGP", "SE": "SWE", "SD": "SDN", "DO": "DOM", "DM": "DMA",
                                     "DJ": "DJI", "DK": "DNK", "VG": "VGB", "DE": "DEU", "YE": "YEM", "DZ": "DZA",
                                     "US": "USA", "UY": "URY", "YT": "MYT", "UM": "UMI", "LB": "LBN", "LC": "LCA",
                                     "LA": "LAO", "TV": "TUV", "TW": "TWN", "TT": "TTO", "TR": "TUR", "LK": "LKA",
                                     "LI": "LIE", "LV": "LVA", "TO": "TON", "LT": "LTU", "LU": "LUX", "LR": "LBR",
                                     "LS": "LSO", "TH": "THA", "TF": "ATF", "TG": "TGO", "TD": "TCD", "TC": "TCA",
                                     "LY": "LBY", "VA": "VAT", "VC": "VCT", "AE": "ARE", "AD": "AND", "AG": "ATG",
                                     "AF": "AFG", "AI": "AIA", "VI": "VIR", "IS": "ISL", "IR": "IRN", "AM": "ARM",
                                     "AL": "ALB", "AO": "AGO", "AQ": "ATA", "AS": "ASM", "AR": "ARG", "AU": "AUS",
                                     "AT": "AUT", "AW": "ABW", "IN": "IND", "AX": "ALA", "AZ": "AZE", "IE": "IRL",
                                     "ID": "IDN", "UA": "UKR", "QA": "QAT", "MZ": "MOZ"]

    static Map colorCodes = ["0":"ZERO", "10":"TEN",
                             "20": "TWENTY", "30": "THIRTY",
                             "40": "FORTY", "50":"FIFTY",
                             "60":"SIXTY", "70":"SEVENTY",
                             "80":"EIGHTY", "90":"NINETY",
                             "100": "HUNDRED"]

    static def hundredColorMap = null


    //http://www.strangeplanet.fr/work/gradient-generator/?c=101:3CAD3C:FFEE05:DBCB21:B8FF05:F2FF00:FFD900:FF7B00:FF6600:FF0000:F00E38:DB1839:C71E3A:A62D41:80333F:70373F:1F1A1F:000000
    static def colorGradient = ["#3CAD3C", "#57B634", "#73BF2C", "#8FC824", "#ABD21C", "#C7DB14", "#E3E40C", "#FFEE05",
                                "#F9E909", "#F4E40D", "#EFDF11", "#EADA15", "#E5D519", "#E0D01D", "#DBCB21", "#D6D21D",
                                "#D1D919", "#CCE115", "#C7E811", "#C2F00D", "#BDF709", "#B8FF05", "#C0FF04", "#C8FF03",
                                "#D0FF02", "#D9FF02", "#E1FF01", "#E9FF00", "#F2FF00", "#F4F800", "#F6F200", "#F8EC00",
                                "#FAE500", "#FCDF00", "#FFD900", "#FFC900", "#FFB900", "#FFAA00", "#FF9A00", "#FF8A00",
                                "#FF7B00", "#FF7700", "#FF7400", "#FF7000", "#FF6D00", "#FF6900", "#FF6600", "#FF5500",
                                "#FF4400", "#FF3300", "#FF2200", "#FF1000", "#FF0000", "#FC0209", "#FA0412", "#F7071C",
                                "#F50925", "#F20B2E", "#F00E38", "#EC0F38", "#E91138", "#E51338", "#E21438", "#DE1638",
                                "#DB1839", "#D71939", "#D41A39", "#D11B39", "#CD1C39", "#CA1D39", "#C71E3A", "#C1203B",
                                "#BC233C", "#B6253D", "#B1283E", "#AB2A3F", "#A62D41", "#9F2E40", "#992F40", "#933040",
                                "#8C313F", "#86323F", "#80333F", "#7D333F", "#7A343F", "#78353F", "#75353F", "#72363F",
                                "#70373F", "#623239", "#552D34", "#47282F", "#3A2329", "#2C1E24", "#1F1A1F", "#191519",
                                "#141114", "#0F0D0F", "#0A080A", "#050405", "#000000"]



    static constraints = {
    }
}
