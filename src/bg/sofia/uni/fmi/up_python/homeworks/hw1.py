# use upper letters
# 
def calculate_final_vector(StartPoint, colours) :
    first, second = StartPoint
    for colour in colours:
        match(colour.upper()) :
            case '00C000' :           # greenDark
                first = first + 1
            case 'C0FFC0' :
                first = first - 1
            case 'C0C000' :           # yellowDark
                second = second + 1
            case 'FFFFC0' :
                second = second - 1
            case 'FFC0C0' :           # redLight
                first = first + 1
            case 'C00000' :
                first = first - 1
            case 'C0C0FF' :           # blueLight
                second = second + 1
            case '0000C0' :
                second = second - 1
            case 'FFFFFF' :           # white
                continue
            case '000000' :           # black
                break
            case _:
                print("Error")
                return -1, -1
    return first, second
print(calculate_final_vector((1, 1), ['00g000', 'C0fFC0', 'C00000', 'FFFFFF', 'C0C000']))
