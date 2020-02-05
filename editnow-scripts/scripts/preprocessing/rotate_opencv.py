#!/usr/bin/env python

from sys import argv
import cv2

path = argv[1]
imageName = argv[2]
processedImageName = argv[3]
angle = int(argv[4])  # -90

# load image
image = cv2.imread(path + imageName)

# ### work
rows, cols = image.shape[:2]
center = (cols / 2, rows / 2)
M = cv2.getRotationMatrix2D(center, angle, 1)
image = cv2.warpAffine(image, M, (cols, rows))
# ### end work

# save image
cv2.imwrite(path + processedImageName, image)