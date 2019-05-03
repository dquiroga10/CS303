from random import randint, choice
from math import ceil
from time import time

boo = False

while boo == False:

	original = input("What is your unordered Array? ")


	delim = ["[", "]", ",", " ", ""]

	A = list()

	original = original.replace("[", "")

	original = original.replace("]", "")

	for i in original.split(","):

		A.append(int(i)) # will bring up an error if the number is not an integer

	if len(A) > 1: #make sure there is an array that has at least two elements

		boo = True


k = int(input("What is your k? "))

while k < 1 or k > len(A):

	k = int(input("What is your k? ")) # making sure of the correct k 




#Algorithm 1
def select(S, ks):

	splitter = choice(S)
	# print("k", k)
	# print("splitter", splitter)
	lower = list()

	upper = list()

	equal = list()

	for i in S:

		if i < splitter:

			lower.append(i)

		elif i > splitter:

			upper.append(i)

		elif i == splitter:

			equal.append(i)
	# print("lower", lower)
	# print("upper", upper)
	# print("equal", equal)
	if len(lower) == ks - 1:

		return splitter

	elif len(lower) >= ks:
		# if len(lower) + len(equal) < k + len(equal):
		# 	return splitter
		return select(lower, ks)

	elif len(lower) < ks - 1:

		if len(lower) + len(equal) >= ks:

			return splitter

		return select(upper, ks - len(lower) - len(equal)) # do len(equal) instead of -1 because there may be multiples of the same splitter that is not taken into account
#print(A)


#Algorithm 2
def det_select(S, ks):
	if(len(S) <= 10):

		return quicksort(S, ks)

		# q = quicksortAlgo(S)

		# return q[ks-1]

	x = []

	array = []

	for i in range(len(S)):

		if len(array) < 5 and i != len(S)-1:

			array.append(S[i])

		if len(array) == 5:

			x = add_partition(x, array)

			array = []

		elif i == len(S) - 1:

			array.append(S[i])

			if(len(array)!=0):

				x = add_partition(x, array)

	count = 0

	for i in x:

		if len(i) == 5:

			x[count] = det_select(i, 3)

		else:

			x[count] = det_select(i, ceil(len(i)/2))

		count += 1

	m = det_select(x, ceil(len(S)/10))

	lower = []

	upper = []

	equal = []

	for i in S:

		if i < m:

			lower.append(i)

		elif i > m:

			upper.append(i)

		elif i == m:

			equal.append(i)

	if (ks <= len(lower)):

		return det_select(lower,ks)

	elif (ks > len(lower)+len(equal)):

		return det_select(upper, ks - len(lower) - len(equal))

	else: return m



def add_partition(S, array):

	S.append(array)

	return S




#Algorithm 3
def quicksort(S, k):

	q = quicksortAlgo(S)

	return q[k-1] # indicies start at 0 so we want the index of k-1 for k'th smallest in a sorted array

def quicksortAlgo(S):

	lower = list()

	upper = list()

	equal = list()

	if len(S) <= 3:

		# if len(S) == 0:

		# 	return [ ]
		#print("sorted", sorted(S))
		return sorted(S)

	else:

		splitter = choice(S)

		for i in S:

			if i < splitter:

				lower.append(i)

			elif i > splitter:

				upper.append(i)

			elif i == splitter:

				equal.append(i)
		# print(lower)
		# print(equal)
		# print(upper)
		return quicksortAlgo(lower) + equal + quicksortAlgo(upper)
#print(A)
print("Randomized Selection for given array:",select(A, k))

print("Deterministic Slection for given array:", det_select(A, k))

print("Quicksort for given array:",quicksort(A, k))



def main():
	A = list()

	count = 0

	start = time()

	while count != 10000000:

		A.append(randint(0, 100000))

		count += 1

	end = time()

	print("Created randomized array of size 10^7 in {} seconds".format(ceil(end - start)))

	startS = time()

	s = select(A, ceil(count/2))

	endS = time()

	print("Random Selection: {} computed in {} seconds".format(s, ceil(endS - startS)))

	startD = time()

	d = det_select(A, ceil(count/2))

	endD = time()

	print("Deterministic Selection: {} computed in {} seconds".format(d, ceil(endD - startD)))

	startQ = time()

	q = quicksort(A, ceil(count/2))

	endQ = time()

	print("Quicksort: {} computed in {} seconds".format(q, ceil(endQ - startQ)))

main()









