#!/usr/bin/env python

from sys import argv
import cv2
import numpy as np


def morph_types(morphType):
    return {
       0: cv2.MORPH_OPEN,
       1: cv2.MORPH_CLOSE,
       2: cv2.MORPH_GRADIENT,
       3: cv2.MORPH_TOPHAT,
       4: cv2.MORPH_BLACKHAT,
       5: cv2.MORPH_DILATE,
       6: cv2.MORPH_ERODE
    }.get(morphType, None)


path = argv[1]
imageName = argv[2]
processedImageName = argv[3]
kernelRows = int(argv[4])
kernelCols = int(argv[5])
morphTypeIndex = int(argv[6])

# calculate before
morphType = morph_types(morphTypeIndex)

# load image
image = cv2.imread(path + imageName)

# ### work
kernel = np.ones((kernelRows, kernelCols), np.uint8)
image = cv2.morphologyEx(image, morphType, kernel)  # iterations = X
# ### end work

# save image
cv2.imwrite(path + processedImageName, image)