import axios from "axios";

export const Api =axios.create({
    baseURL:import.meta.env.SPRING_API_URL,
    timeout:5000,
    headers:{
        "Content-Type":"application/json",
        
    }})


export const AuthApi= axios.create({
    baseURL:import.meta.env.SPRING_API_URL,
    timeout:5000,
    withCredentials:true,
    headers:{
        "Content-Type":"application/json",
        
    }})
 
AuthApi.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      console.warn("Session expired or unauthorized");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);
