#!/usr/bin/env python

from sys import argv
import cv2

# parse input
path = argv[1]
imageName = argv[2]
processedImageName = argv[3]
kernelWidth = int(argv[4])
kernelHeight = int(argv[5])
sigmaX = int(argv[6])

# load image
image = cv2.imread(path + imageName)

# ### work
image = cv2.GaussianBlur(image, (kernelWidth, kernelHeight), sigmaX)
# ### end work

# save image
cv2.imwrite(path + processedImageName, image)