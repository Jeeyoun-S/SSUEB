import { apiInstance } from "./index.js";

const api = apiInstance();

async function getAttachUrl(no) {
  var result = [];
  await api.get(`${process.env.VUE_APP_API_BASE_URL}/reservation/attach/${no}`)
  .then((res) => {
    for(let i=0; i<res.data.length; i++){
      if(res.data[i].attachFile.endsWith('jpg'))
        result.push(process.env.VUE_APP_FILE_PATH_RESERVATION_IMAGE + res.data[i].attachFile);
      else
        result.push(process.env.VUE_APP_FILE_PATH_RESERVATION_VIDEO + res.data[i].attachFile);
    }
  })
  .catch()
  return await Promise.resolve(result);
}

export { getAttachUrl };
