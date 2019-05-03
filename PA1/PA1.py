from random import *
import sys

nodes = float(input("What is your n? "))

while nodes < 2: 
	nodes = float(input("What is your n? "))

if nodes > 1:
	prob = float(input("Chose a p between [0,1] "))

	while prob < 0 or prob > 1:
		prob = float(input("Chose a p between [0,1] "))

def generate_graph(n, p): #adjancey list 
	G = list()

	for num in range(n):
		G.append(set())

	for x in range(n):
		for y in range(n):
			num = random()
			if num < p and x != y:
				G[x].add(y)
				G[y].add(x)
	for line in range(len(G)):
		G[line] = list(G[line])
	return G

def BFS(s, adj): 
	level = {s : 0}
	parent = {s : None}
	vis = [s]
	frontier = [s]
	i = 1
	while frontier:
		next_up = []
		for u in frontier:
			for v in adj[u]:
				if v not in level:
					level[v] = i
					parent[v] = u 
					next_up.append(v)
					vis.append(v)
		frontier = next_up
		i += 1
	return vis

def error( msg ):
	sys.exit( msg )

def main(n, p, t = None):
	visited = list()
	n = int(n)
	adj_list = generate_graph(n, p)
	if t == None:
		t = float(input("Pick a whole number t greater than 1. What is your t? "))
		while t < 1:
			t = float(input("Sorry pick a whole number t greater than 1. What is your t? "))
		t = int(t)
	for k in range(n):
		if k not in visited:
			vis_search = BFS(k, adj_list)
			for k in vis_search:
				if k in visited:
					error("counted twice one node")
				visited.append(k)
			if len(vis_search) >= t:
				return 1
	return 0

print(main(nodes, prob))

def testing( c, n = 40):
	p = float(c)/float(n)
	i = 1 
	count =0
	while i <= 500: 
		ret_value = main(n, p, t = 30)
		if ret_value == 1:
			count += 1
		i += 1
	#print(count)
	return float(count/500)

def mainTest():
	print("Testing.. for c [.2,3] increment by .2. 500 random graphs for each c")
	c = .2
	while c <= 3:
		percentage = testing(c)
		print("c-value: {} percentage: {} of conncections out of 500 graphs".format(c, percentage))
		c += .2
		c = round(c, 1)
	print("Ran 7500 randomly generated graphs. 500 gaphs for each c")

mainTest()
