import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import LoginContextProvider from './contexts/LoginContextProvider';
import Home from './pages/Home';
import Join from './pages/Join';
import About from './pages/About';
import User from './pages/User';
import Login from './pages/Login';

function App() {
  return (

    <BrowserRouter>
      <LoginContextProvider>
        <Routes>
          <Route path="/" element={<Home/>}></Route>
          <Route path="/login" element={<Login/>}></Route>
          <Route path="/join" element={<Join/>}></Route>
          <Route path="/user" element={<User/>}></Route>
          <Route path="/about" element={<About/>}></Route>
        </Routes>
      </LoginContextProvider>
    </BrowserRouter>
  );
}

export default App;
