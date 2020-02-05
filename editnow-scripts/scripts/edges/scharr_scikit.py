#!/usr/bin/env python

from sys import argv
from skimage.filters import scharr
from skimage import io

path = argv[1]
imageName = argv[2]
processedImageName = argv[3]

# load image
image = io.imread(path + imageName)

if len(image.shape) == 3:
    image = image[:, :, 0]  #RGB -> grayscale
image = scharr(image)

# save image
io.imsave(path + processedImageName, image)



