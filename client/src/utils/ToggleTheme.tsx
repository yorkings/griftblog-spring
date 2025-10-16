
import { FaSun ,FaMoon} from "react-icons/fa"
import { useTheme,type Theme } from "./ThemeContext"


const ToggleTheme = () => {
  const {theme,setTheme}=useTheme()  

  const ThemeSwitcher=()=>{
    const nextTheme:Theme= theme == "light"? "dark":"light"
    setTheme(nextTheme)
  }
  const ThemeIcons=(currentheme:Theme)=>{
    switch (currentheme) {
        case "light":
            return <FaSun/>
    
        case "dark":
            return <FaMoon/>
    }
  }
  
  return (
  <button  onClick={ThemeSwitcher} className="relative flex items-center w-16  md:w-20 h-8 bg-gray-300 dark:bg-[var(--switch-dark)] rounded-full p-1 transition-colors duration-500 ease-in-out">
      <div className={`absolute top-1 left-1 w-6  h-6 rounded-full flex items-center justify-center text-white text-lg  lg:text-xl transition-all duration-500 ease-in-out  font-extrabold ${theme === "light" ? "translate-x-0 bg-[var(--light-subheader)] text-yellow-500" : "translate-x-8 bg-[var(--dark-header-color)]  text-blue-900 "}`}>
        {ThemeIcons(theme)}
      </div>
    </button>
  )
     
}

export default ToggleTheme
