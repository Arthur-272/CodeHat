import React, { Component } from "react";
import Login from "./LoginComponent";
import RootLogo from "./RootAccessLogo.js";

class MainLogin extends Component {
    render() {
        return (
            <div className="container-fluid" style={{height: "100%", width: "100%"}}>
                <div className="row justify-content-md-center align-items-center">
                    <div className="col col-sx-6">
                        <RootLogo/>
                    </div>
                    <div className="col col-sx-6" style={{backgroundColor: "indigo" }}>
                        <Login />
                    </div>
                </div>
            </div>
        );
    }
}

export default MainLogin;
