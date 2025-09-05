import axios from 'axios';

const BASE_URL=process.env.REACT_APP_BASE_URL;

const fetchGetData = (uri) => {
  console.log("BASE_URL:", BASE_URL);

  
  const url=`${BASE_URL}${uri}`;
  return axios.get(url)
    .catch(error => {
      // Handle exceptions/errors
      console.error('Error fetching data for URL:', url, 'Error', error.message);
      // You can throw the error again if you want to handle it elsewhere
      throw error;
    });
};

const fetchPostData=(uri,payload)=>{
  const url=`${BASE_URL}${uri}`;
  return axios.post(url,payload)
    .catch(error=>{
      //Handle exceptions/errors
      console.error('Error fetching data for URL:',url,'Error',error.message);

      //You can throe the error again if you want to handle it elsewhere
      throw error;
    });
};

export default fetchGetData;
export {fetchPostData};