const BASE_64_CONST = ';base64,';
const DATA_INPUT_IMAGE_CONST = 'data:image/';

export default {

  getImageStructureFromFullImageBase64(fullImageBase64) {
    const indexOfBase64Const = fullImageBase64.indexOf(BASE_64_CONST);
    const base64 = fullImageBase64.substring(indexOfBase64Const + BASE_64_CONST.length);
    const type = fullImageBase64.substring(
      fullImageBase64.indexOf(DATA_INPUT_IMAGE_CONST) + DATA_INPUT_IMAGE_CONST.length,
      indexOfBase64Const);

    const image = {
      fullImageBase64: fullImageBase64,
      base64: base64,
      type: type,
      width: null,
      height: null
    };

    this.bindMetadataToImage(image, fullImageBase64);

    return image
  },
  getImageStructureFromResponse(response) {
    const image = {
      fullImageBase64: DATA_INPUT_IMAGE_CONST + response.data.type +  BASE_64_CONST + response.data.base64,
      base64: response.data.base64,
      type: response.data.type,
      width: null,
      height: null
    };

    this.bindMetadataToImage(image, image.fullImageBase64);

    return image
  },
  bindMetadataToImage(image, fullImageBase64) {
    const imageMetadata = new Image();

    imageMetadata.onload = function() {
      image.width = imageMetadata.naturalWidth;
      image.height = imageMetadata.naturalHeight;
    };

    imageMetadata.src = fullImageBase64;
  },
  getEmptyImageObject() {
    return {
      fullImageBase64: null,
      base64: null,
      type: null,
      width: null,
      height: null
    }
  }

}