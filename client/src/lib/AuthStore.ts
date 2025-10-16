import { create } from "zustand";
import { AuthApi } from "./Api";

interface currentUser {
  id: number;
  username: string;
  email: string; 
}

interface AuthState {
  user: currentUser | null;
  isAuthenticated: boolean;
  loading: boolean;
  fetchUser: (currentUser: currentUser) => void;
  logout: () => Promise<void>;
}

export const AuthStore=create<AuthState>((set)=>({
  user: null,
  isAuthenticated: false,
  loading: true,
  fetchUser:(currentUser)=>{
       set({user:currentUser,isAuthenticated:true,loading:false})
  },
  logout: async () => {
    await AuthApi.post("auth/logout");
    set({ user: null, isAuthenticated: false });
  },
}))