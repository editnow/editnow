#!/usr/bin/env python

import cv2
import numpy as np

# parse input
# argv[1] - images path like "./images/"
# argv[2] - input image name (before processed)
# argv[3} - output image name (after processed)
path = '../../test/'
imageName = 'test_rgb.jpg'
processedImageName = 'test_rgb_out_kmeans.jpg'
reshapeValue = 2
maxIter = 100
epsilon = 2
K = 8

# calculate before

# load image
image = cv2.imread(path + imageName)

# ### work
Z = image.reshape((-1, reshapeValue))

# convert to np.float32
Z = np.float32(Z)

# define criteria, number of clusters(K) and apply kmeans()
criteria = (cv2.TERM_CRITERIA_EPS + cv2.TERM_CRITERIA_MAX_ITER, maxIter, epsilon)
ret,label,center=cv2.kmeans(Z, K, None, criteria, 10, cv2.KMEANS_RANDOM_CENTERS)

# Now convert back into uint8, and make original image
center = np.uint8(center)
res = center[label.flatten()]
image = res.reshape((image.shape))
# ### end work

# save image
cv2.imwrite(path + processedImageName, image)