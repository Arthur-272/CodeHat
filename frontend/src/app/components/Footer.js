import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class Footer extends Component {
  render() {
    return (

      <footer className="container-fluid footer align-items-center justify-content-center">

          <div class="footer-copyright text-center">
          <span>Copyright © <a href="https://www.charusat.ac.in/cspit/it/">Charotar University </a>2021</span>
          <p></p>
          <Link className="navbar-brand " to="/">Home</Link>
          <Link className="navbar-brand " to="/">Scoring System</Link>
          <Link className="navbar-brand " to="/">About us</Link>
          <Link className="navbar-brand " to="/">Contact us</Link>

          </div>
        
      </footer> 
    );
  }
}

export default Footer;