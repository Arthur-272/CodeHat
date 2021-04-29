import React, { Component } from "react";
import { Link, Redirect } from "react-router-dom";
import {
	Container,
	Col,
	Form,
	FormGroup,
	Input,
	Button,
	Row,
	Jumbotron,
} from "reactstrap";
import axios from 'axios'
import backend_url from '../services/api'

class Signup extends Component {
	constructor() {
		super();
		this.state = {
			firstName:'',
			lastName:'',
			mobile:'',
			email:'',
			password:'',
			role:'',
			redirect:null
			
		};
		
	}

	handleChange = e => {
		//console.log(e.target.name)
		this.setState({ [e.target.name]: e.target.value })
		if(e.target.name == "email"){
			//console.log(e.target.value);
			var studentEmail = new RegExp("@charusat.edu.in");
			var facultyEmail = new RegExp("@charusat.ac.in");
			if(studentEmail.test(e.target.value)){
				this.setState({role:'student'})
			}
			if(facultyEmail.test(e.target.value)){
				this.setState({role:'teacher'})
			}
		}
	}

	handleSubmit = e => {
		e.preventDefault();

		//if (this.validate()) 
			console.log(this.state);
			axios.post(`${backend_url}/signup`,this.state).then((response) =>{
				console.log(response)
				if(response.status===200){
					alert("Created Account successfully...");
					this.setState({redirect:"/login"})
				}
				
			},
			(err)=>{
				alert(err.status)
				if(err.status===409){
					alert("user already exists");
				}else{
					alert("There was some problem. Please try again later")
				}
			}
			);
			
		
	}

	validate() {
		let input = this.state.email;
		let errors = {};
		let isValid = true;
		var studentEmail = new RegExp("@charusat.edu.in");
		var facultyEmail = new RegExp("@charusat.ac.in");

		if (
			!studentEmail.test(input["email"]) &&
			!facultyEmail.test(input["email"])
		) {
			isValid = false;
			errors["email"] = "Please enter Charusat email.";
		}

		if (!input) {
			isValid = false;
			errors["email"] = "Please enter your email Address.";
		}else{
			var pattern = new RegExp(
				/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i
			);
			if (!pattern.test(input)) {
				isValid = false;
				errors["email"] = "Please enter valid email address.";
			}
		}

		this.setState({
			errors: errors,
		});
		return isValid;
	}

	render() {
		if (this.state.redirect) {
			return <Redirect to={this.state.redirect} />
		  }
		const { firstName, lastName, mobile, email, password } = this.state
		const jumbotronStyle = {
			backgroundColor: "white",
			borderRadius: "50px",
			marginTop: "100px",
			marginBottom: "80px",
			maxWidth: "560px",
		};

		const head1Style = {
			fontWeight: "bold",
			alignSelf: "center",
			backgroundColor: "#EBEBEB",
			borderRadius: "50px",
			width: "28vh",
			maxWidth: "200px",
			maxHeight: "250px",
			height: "60px",
			marginBottom: "20px",
		};

		const head2Style = {
			fontWeight: "bold",
			alignSelf: "center",
			backgroundColor: "#EBEBEB",
			borderRadius: "50px",
			width: "45vh",
			height: "60px",
			maxWidth: "500px",
			maxHeight: "160px",
			marginBottom: "20px",
			marginTop: "28px",
		};

		const verifyEmailStyle = {
			fontWeight: "bold",
			textAlign: "left",
			marginTop: "36px",
			marginBottom: "22px",
		};

		const head4Style = {
			fontWeight: "bold",
			textAlign: "left",
		};

		const inputStyle = {
			backgroundColor: "#EBEBEB",
			borderRadius: "50px",
			fontSize: "24px",
			maxWidth: "400px",
		};

		const buttonStyle = {
			fontWeight: "bold",
			backgroundColor: "indigo",
			borderRadius: "50px",
			width: "325px",
			marginTop: "40px",
			marginBottom: "25px",
		};

		return (
			<Container>
				<Row>
					<Col>
						<center>
							<Jumbotron
								className="jumbotron"
								style={jumbotronStyle}>
								<center>
									<h1 style={head1Style}>Sign Up</h1>
								</center>
								<Form
									onSubmit={this.handleSubmit}
									className="form">
									<Col>
										<FormGroup>
											<h4 style={head4Style}>Email</h4>
											<div align="left">
												<Input
													type="email"
													name="email"
													id="exampleEmail"
													value={email}
													onChange={this.handleChange}
													style={inputStyle}
												/>
											</div>
										</FormGroup>
									</Col>
									<br />
									<Col>
										<FormGroup>
											<h4 style={head4Style}>Password</h4>
											<div align="left">
												<Input
													type="password"
													name="password"
													id="examplePassword"
													onChange={this.handleChange}
													style={inputStyle}
													value={password}
												/>
											</div>
										</FormGroup>
									</Col>
									<Col>
										<FormGroup>
											<h4 style={head4Style}>
												First Name
											</h4>
											<div align="left">
												<Input
													type="text"
													name="firstName"
													id="exampleEmail"
													onChange={this.handleChange}
													style={inputStyle}
													value={firstName}
												/>
											</div>
										</FormGroup>
									</Col>
									<Col>
										<FormGroup>
											<h4 style={head4Style}>
												Last Name
											</h4>
											<div align="left">
												<Input
													type="text"
													name="lastName"
													id="exampleEmail"
													onChange={this.handleChange}
													style={inputStyle}
													value={lastName}
												/>
											</div>
										</FormGroup>
									</Col>
									<br />
									<Col>
										<FormGroup>
											<h4 style={head4Style}>
												Phone Number
											</h4>
											<div align="left">
												<Input
													type="phone"
													name="mobile"
													onChange={this.handleChange}
													style={inputStyle}
													value={mobile}
												/>
											</div>
										</FormGroup>
									</Col>
									<Button type="submit" style={buttonStyle}>
										<h2>Finish Sign Up</h2>
									</Button>
								</Form>
								<h2>Sign Up With: </h2>
							</Jumbotron>
							<h2
								style={{
									color: "white",
									marginBottom: "100px",
								}}
							>
								Already a Member ?{" "}
								<Link to="/login">Sign In</Link>
							</h2>
						</center>
					</Col>
				</Row>
			</Container>
		);
	}
}

export default Signup;
