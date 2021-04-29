import React, { Component } from "react";
import { Container } from "reactstrap";
import rootlogo from "./styles/images/root-access-1.png";

class RootLogo extends Component {
	render() {
		return (
			<Container className="container">
				<div className="col justify-content-md-center align-items-center">
					<div className="row-1"></div>
					<div className="row-4 justify-content-md-center text-center">
						<h1
							className="root-access"
							style={{
								fontSize: "72px",
								textShadow: "revert",
								marginBottom: "100px",
							}}
						>
							Welcome to CHARUSAT CodeHat
						</h1>
					</div>
					<div className="row-1"></div>
					<div className="row-6">
						<img
							className="img-responsive center img-thumbnail"
							src={rootlogo}
							alt="Root Computer"
						/>
					</div>
				</div>
			</Container>
		);
	}
}

export default RootLogo;
