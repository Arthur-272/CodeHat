import React, { Component } from "react";
import Signup from "./SignupComponent";
import RootLogo from "./RootAccessLogo.js";

class MainSignup extends Component {
	render() {
		return (
			<div
				className="container-fluid"
				style={{ height: "100%", width: "100%" }}
			>
				<div className="row justify-content-md-center align-items-center">
					<div className="col col-sx-6">
						<RootLogo />
					</div>
					<div
						className="col col-sx-6"
						style={{ backgroundColor: "indigo" }}
					>
						<Signup />
					</div>
				</div>
			</div>
		);
	}
}

export default MainSignup;
