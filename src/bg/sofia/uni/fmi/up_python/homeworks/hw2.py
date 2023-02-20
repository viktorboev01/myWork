def calculate_letter(prev_num, counter):
    match(prev_num):
        case 0: 
            return ' '
        case 2: 
            return chr(ord('A') + counter)
        case 3: 
            return chr(ord('D') + counter)
        case 4: 
            return chr(ord('G') + counter)
        case 5: 
            return chr(ord('J') + counter)
        case 6: 
            return chr(ord('M') + counter)
        case 7: 
            return chr(ord('P') + counter)
        case 8: 
            return chr(ord('T') + counter)
        case 9: 
            return chr(ord('W') + counter)
    return ""
                
def nums_to_text(nums):
    text = ""
    prev_num = -2
    counter = 0
    for number in nums:
        if number == 1:
            continue
        if number < -1 or number > 9:
            print("Error")
            return "Invalid parameters"
        if prev_num == number:
            counter += 1
        else:
            if prev_num in (7, 9):
                counter %= 4
            else:
                counter %= 3
            text += calculate_letter(prev_num, counter)
            counter = 0
            prev_num = number
    text += calculate_letter(prev_num, counter)
    return text

def text_to_nums(text):
    text = text.upper()

    # first letters in sections 2-9
    compare_values = ord('A'), ord('D'), ord('G'), ord('J'), ord('M'), ord('P'), ord('T'), ord('W')

    nums_list = []
    number = 2
    prev_num = -2 
    for letter in text:
        if letter == ' ':
            nums_list.append(0)
        else:
            for value in compare_values:
                if number in (7, 9):
                    length = 4
                else:
                    length = 3
                counter = ord(letter) - value
                if (counter < length):
                    if prev_num == number:
                        nums_list.append(-1)
                    while counter >= 0:
                        nums_list.append(number)
                        counter -= 1
                    prev_num = number 
                    number = 2
                    break
                number += 1  
    return nums_list

def nums_to_angle(nums):
    nums = nums * 30
    return sum(nums) % 360

def angles_to_nums(angles):
    nums_list = []
    for angle in angles:
        angle %= 360
        num = int(angle / 30)
        if angle % 30 > 15:
            num += 1
        if num > 0 and num < 12:
            nums_list.append(num)
    return nums_list 

def is_phone_tastic(word):
    return nums_to_angle(text_to_nums(word)) % len(word) == 0
