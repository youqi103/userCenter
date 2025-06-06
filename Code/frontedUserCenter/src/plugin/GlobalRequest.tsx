import { history } from '@umijs/max';
import { message }from 'antd';
import { extend } from 'umi-request';

const request = extend({
  credentials: 'include',
  prefix: process.env.NODE_ENV === 'productioin'?'http://user-backend.cn':undefined
});


request.interceptors.request.use((url, options) => {
    console.log('do request url={}',url);
    
    return {
      url,
      options: {
        ...options,
      },
      interceptors: true,
    };
})

  
request.interceptors.response.use(
    async (response) => {
        const res = await response.clone().json();
    if (res.code === 200) {
        return res.data;
    }
    if (res.code === 400100) {
        message.error('请先登录');
        const urlParams = new URL(window.location.href).searchParams;
        history.push(urlParams.get('redirect') || '/');
    } else {
        console.log("request error");
        message.error(res.description);
    }
    return res.data;
 })

export default request;