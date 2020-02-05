import numpy as np

from skimage.segmentation import random_walker
from skimage.data import binary_blobs
from skimage.exposure import rescale_intensity
import skimage
from skimage import io

path = '../../test/'
imageName = 'test_astronaut_output.png'
processedImageName = 'test_random_walker.png'
markersNumber = 4
#tymczasowe tworzenie przedzialow dla markerow - zakreś <-1, 1>
markerBorders = [-1, -0.4, 0.6, 0.9]

# data = io.imread(path + imageName)

# Generate noisy synthetic data
data = skimage.img_as_float(binary_blobs(length=128, seed=1))
sigma = 0.35
data += np.random.normal(loc=0, scale=sigma, size=data.shape)
data = rescale_intensity(data, in_range=(-sigma, 1 + sigma),
                         out_range=(-1, 1))

# The range of the binary image spans over (-1, 1).
# We choose the hottest and the coldest pixels as markers.
markers = np.zeros(data.shape, dtype=np.uint)
# for markerNumber in range(markersNumber):
#     markers[data > markerBorders[markerNumber]] = markerNumber  # dodac (data < 0.5) & (data > 0.2) itd.

markers[data < -0.55] = 1
markers[data > 0.55] = 2
markers[data > 0.75] = 3

# Run random walker algorithm
labels = random_walker(data, markers, beta=10, mode='bf')

print(data)
print(markers)
print(labels)

io.imsave(path + processedImageName, labels)
io.imsave(path + "test_1.png", data)
io.imsave(path + "test_2.png", markers)