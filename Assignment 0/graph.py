import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

# Three different variables according to axes declared.
probability, border_width, time_taken = [] ,[] , []

# opening output.txt file. 
with open('/Users/apple/Desktop/Programmer/output.txt') as result:
	file = result.read()
	file = file.replace('\n', ',').split(',')

# modifications with the data 
i=0
while i < len(file) - 3:
  probability.append(float(file[i]))
  border_width.append(int(file[i+1]))
  time_taken.append((float(int(file[i+2]))))
  i+=3

# plotting the figure 
fig = plt.figure()
graph = fig.add_subplot(111, projection='3d', xlabel='Probability', ylabel='Border', zlabel='Time_taken (s)')
graph.plot(xs=probability, ys=border_width, zs=time_taken)
graph.ticklabel_format(useOffset=False, style='plain')
plt.savefig('graph.png')