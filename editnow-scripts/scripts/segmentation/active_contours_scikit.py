#!/usr/bin/env python

from sys import argv
import numpy as np
from skimage.filters import gaussian
from skimage.segmentation import active_contour
from skimage import io


# parse input
# argv[1] - images path like "./images/"
# argv[2] - input image name (before processed)
# argv[3} - output image name (after processed)
path = '../../test/'
imageName = 'test_astronaut_2.jpg'
processedImageName = 'test_astronaut_output.png'


# load image
image = io.imread(path + imageName)

# ### work
s = np.linspace(0, 2*np.pi, 400)
r = 100 + 100*np.sin(s)
c = 220 + 100*np.cos(s)
init = np.array([r, c]).T

snake = active_contour(gaussian(image, 3), init, alpha=0.015, beta=10, gamma=0.001)

print(snake)    # float
print(image)    # int   conflict

# ### end work

# save image
io.imsave(path + processedImageName, image)