#!/usr/bin/env python

from sys import argv
import cv2


def interpolation_types(interpolationTypeIndex):
    return {
       0: cv2.INTER_NEAREST,
       1: cv2.INTER_LINEAR,
       2: cv2.INTER_CUBIC,
       3: cv2.INTER_AREA,
       4: cv2.INTER_LANCZOS4
    }.get(interpolationTypeIndex, None)

#
path = argv[1]
imageName = argv[2]
processedImageName = argv[3]
width = int(argv[4])
height = int(argv[5])
interpolationTypeIndex = int(argv[6])

# path = '../'
# imageName = 'edges2shoes.jpg'
# processedImageName = 'edges2shoes_result.jpg'
# width = 1024
# height = 512
# interpolationTypeIndex = 2

interpolationType = interpolation_types(interpolationTypeIndex)

# load image
image = cv2.imread(path + imageName)

resizedImage = cv2.resize(image, (width, height), interpolation=interpolationType)

# save image
cv2.imwrite(path + processedImageName, resizedImage)
