#!/usr/bin/env python

from sys import argv
import cv2


def threshold_types(thresholdType):
    return {
       0: cv2.THRESH_BINARY,
       1: cv2.THRESH_BINARY_INV,
       2: cv2.THRESH_TRUNC,
       3: cv2.THRESH_TOZERO,
       4: cv2.THRESH_TOZERO_INV
    }.get(thresholdType, None)


# parse input
# argv[1] - images path like "./images/"
# argv[2] - input image name (before processed)
# argv[3} - output image name (after processed)
path = argv[1]
imageName = argv[2]
processedImageName = argv[3]
threshold = int(argv[4])
maxval = int(argv[5])
thresholdTypeIndex = int(argv[6])

# calculate before
thresholdType = threshold_types(thresholdTypeIndex)

# load image
image = cv2.imread(path + imageName)

# ### work
retval, image = cv2.threshold(image, threshold, maxval, thresholdType)
# ### end work

# save image
cv2.imwrite(path + processedImageName, image)