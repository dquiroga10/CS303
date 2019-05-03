original = str(input("What is your string? Type 'default' or hit ENTER to use the string from Homework file 3. "))

# inspired Willie Yao and GeeksforGeeks for the dynamic Programming Algorithm
# used developer.rhino3d.com to figure out string manipulation

if original == "default" or original == "": 

    original = "Buffy the Vampire Slayer fans are sure to get their fix with the DVD release of the show's first season. \
    The three-disc collection includes all 12 episodes as well as many extras. There is a collection of interviews \
    by the show's creator Joss Whedon in which he explains his inspiration for the show as well as comments on the \
    various cast members. Much of the same material is covered in more depth with Whedon's commentary track for the \
    show's first two episodes that make up the Buffy the Vampire Slayer pilot. The most interesting points of Whedon's \
    commentary come from his explanation of the learning curve he encountered shifting from blockbuster films like Toy \
    Story to a much lower-budget television series. The first disc also includes a short interview with David Boreanaz \
    who plays the role of Angel. Other features include the script for the pilot episodes, a trailer, a large photo gallery \
    of publicity shots and in-depth biographies of Whedon and several of the show's stars, including Sarah Michelle Gellar, \
    Alyson Hannigan and Nicholas Brendon."

original_split = original.split()

statement_len = len(original_split)


m = int(input("What length will you like each line to be? i.e.: what is your M? "))

max_fit = []

lost_space = [] 


for i in range(0, statement_len):

    total = -1

    space= 0

    while total <= m and i + space< statement_len:

        total = total + len(original_split[i+space]) + 1

        space = space + 1

    max_fit.append(space - 1)

# populate lost_space
for i in range(0, statement_len):

    c = []

    n = m + 1

    higher = max_fit[i] + i

    for j in range(i, higher):

        n -= len(original_split[j]) + 1

        c.append(n**3) 

    lost_space.append(c)

best_fit = [0]*statement_len

final = [0]*statement_len 

def paragraph_final(n):

    smallest = m**3

    for i in range(0, max_fit[n]):

        t = lost_space[n][i]

        if i+1 < statement_len:

            t += best_fit[n+i+1]

        if t < smallest:

            smallest = t

            final[n] = i

    return smallest


temp_num = -1

for i in range(statement_len-1, -1, -1):

    if len(original_split[i]) + temp_num + 1 <= m:
        
        temp_num += len(original_split[i]) + 1

        final[i] = statement_len

    else:

        break

for i in range (i, -1, -1):

    best_fit[i] = paragraph_final(i) # make the index whatever is returned from the function that is doing the math


index = 0

for i in max_fit:

    count = 0

    string = ""

    while count < i and count != i and index < len(original_split):

        string += original_split[index] + " "

        index += 1

        count += 1

    print(string)

    if index >= len(original_split):

        break

print( "penalty:", best_fit[0])

