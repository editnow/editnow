#!/usr/bin/env python

from sys import argv
import cv2

# parse input
path = argv[1]
imageName = argv[2]
processedImageName = argv[3]
d = int(argv[4])
sigmaColor = int(argv[5])
sigmaSpace = int(argv[6])

# load image
image = cv2.imread(path + imageName)

# ### work
image = cv2.bilateralFilter(image, d, sigmaColor, sigmaSpace)
# ### end work

# save image
cv2.imwrite(path + processedImageName, image)