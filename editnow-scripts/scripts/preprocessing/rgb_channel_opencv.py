#!/usr/bin/env python

import cv2

path = './'
imageName = 'test_rgb.jpg'
processedImageName = 'test_rgb_out.jpg'
disableRed = True
disableGreen = True
disableBlue = False

print(path + imageName)

# load image
image = cv2.imread(path + imageName)

# ### work
if disableRed:
    image[:, :, 2] = 0
if disableGreen:
    image[:, :, 1] = 0
if disableBlue:
    image[:, :, 0] = 0
# ### end work

# save image
cv2.imwrite(path + processedImageName, image)