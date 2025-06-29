import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import Home from './components/Home';



function App() {
  
  return (
    <BrowserRouter>
        <Routes>
        {/* <Route path="/" element={<Navigate to="/login" />} /> Redirect to login */}
        <Route path="/" element={<Home />} /> {/* Redirect to login */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        </Routes>
    </BrowserRouter>
  )
}

export default App