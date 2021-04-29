import React, { Component } from 'react';
import { Dropdown } from 'react-bootstrap';
import { Link,Redirect } from 'react-router-dom';
import { Trans } from 'react-i18next';
import axios from 'axios';
import background_url from '../services/api.js';
class Navbar extends Component {
  constructor(){
    super();
    this.state = {
      userName: '',
      isLoggedIn: false,
      redirect:null,
      isFaculty: false
    }
    this.checkState();
  }
  checkState=()=>{
    if(localStorage.getItem("user_id")!=null) {
      
      axios.get(background_url+'/user/'+localStorage.getItem("user_id")).then((response)=>{
        console.log(response.data);
        this.setState({userName:response.data.firstName+' '+response.data.lastName});
        this.setState({isLoggedIn:true})
        if(response.data.role==='student'){
          this.setState({isFaculty:false})
        }else{
          this.setState({isFaculty:true})
        }
        console.log(this.state.isLoggedIn)
        console.log(this.state.userName)
        console.log("user is faculty " + this.state.isFaculty)
      }).catch((err)=>{
        alert(err);
      })
    }
    console.log(this.state.userName);
    console.log(localStorage.getItem("user_id"));
  }
  signout=()=>{
    // localStorage.removeItem("user_id");
    // this.setState({redirect:'/login'});
  }
  render() {
    // if(this.state.redirect)
    //   return <Redirect to={this.state.redirect} />
    return (
      <nav className="navbar default-layout-navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
        <div className="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
        <Link className="navbar-brand brand-logo" to="/"><img src={require('../../assets/images/logo1.png')} alt="logo" /></Link>
          <Link className="navbar-brand brand-logo" to="/"><img src={require('../../assets/images/logo2.png')} alt="logo" /></Link>
          
        </div>
        <div className="navbar-menu-wrapper d-flex align-items-stretch">
          <div className="navbar-menu-wrapper d-flex align-items-stretch">
          
          <div className="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
            <Link className="navbar-brand brand-logo" to={"/classes"}>Home</Link>
          </div>
          <div className="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
            <Link className="navbar-brand brand-logo" to={"/practice"}>Practice</Link>
          </div>
          <div className="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
            <Link className="navbar-brand brand-logo" to={"/leaderboard"}>Leaderboard</Link>
          </div>


            <div className="search-field d-none d-md-block">
              <form className="d-flex align-items-center h-100" action="#">
                <div className="input-group">
                  <div className="input-group-prepend bg-transparent">
                    <i className="input-group-text border-0 mdi mdi-magnify"></i>
                  </div>
                  <input type="text" className="form-control bg-transparent border-0" placeholder="Search" />
                </div>
              </form>
            </div>

            <ul className="navbar-nav navbar-nav-right">
              {
              (this.state.isLoggedIn==true) ?
                (<><li className="nav-item nav-profile">
                  <Dropdown alignRight>
                    <Dropdown.Toggle className="nav-link">
                      <div className="nav-profile-img">
                      </div>
                      <div className="nav-profile-text">
                        <p className="mb-1 text-black"><Trans>{this.state.userName}</Trans></p>
                      </div>
                    </Dropdown.Toggle>

                    <Dropdown.Menu className="navbar-dropdown">
                      <Dropdown.Item href="/loginSuccess" onClick={evt => evt.preventDefault()}>
                        <i className="mdi mdi-cached mr-2 text-success"></i>
                        {(this.state.isFaculty === true)?<Link to="/faculty"><Trans>My Profile</Trans></Link>:<Link to="/loginSuccess"><Trans>My Profile</Trans></Link>}
                        
                      </Dropdown.Item>
                      
                    </Dropdown.Menu>
                  </Dropdown>
                </li>


                  <li className="nav-item nav-logout d-none d-lg-block">
                    <a className="nav-link" href="/login" onClick={e=>localStorage.removeItem("user_id")}>
                      <i className="mdi mdi-power"></i>
                    </a>
                  </li></>) :
                null
                }

            </ul>
            <button className="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" onClick={this.toggleOffcanvas}>
              <span className="mdi mdi-menu"></span>
            </button>
          </div>
        </div>
      </nav>
    );
  }
}

export default Navbar;