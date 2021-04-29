import React, { Component } from "react";
import { Container,Jumbotron } from "reactstrap";
import Editor from "react-simple-code-editor";
import {Redirect } from "react-router-dom";
// import { highlight, languages } from "prismjs/components/prism-core";
// import "prismjs/components/prism-clike";
// import "prismjs/components/prism-javascript";
// import "prismjs/themes/prism.css";
import {
	Button,
	Form,
	FormGroup,
	Label,
	Input,
	FormText,
} from "reactstrap";
import DropdownButton from 'react-bootstrap/DropdownButton';
import Dropdown from 'react-bootstrap/Dropdown'

import backend_url from '../services/api';
import axios from 'axios';

import SimpleEditor from './MonacoEditor';

function CodeEditor() {
	const [code, setCode] = React.useState(
		`function add(a, b) {\n  return a + b;\n}`
	);
	return (
		<Container
			style={{
				margin: "auto",
				padding: "10px",
				// backgroundColor: "#181818",
				border: "2px solid black",
				borderRadius: "25px",
			}}
		>
			<Editor
				value={code}
				onValueChange={(code) => setCode(code)}
				// highlight={(code) => highlight(code, languages.js)}
				padding={10}
				style={{
					fontFamily: '"Fira code", "Fira Mono", monospace',
					fontSize: 18,
					minHeight: "300px",
				}}
			/>
		</Container>
	);
}


class ProblemDefinitionPage extends Component {

	
		state = {
			problems: {},
			language:'',
			testCases:[],

		}


	componentDidMount() {
		const id = localStorage.getItem('problemId')
		axios.get(`${backend_url}/problems/id/${id}`).then
		(response =>{
			//console.log(response.data)
			this.setState({problems:response.data})
			console.log(this.state.problems)
		})
		.catch(err =>{
			console.log(err)
		})

		axios.get(`${backend_url}/problem/${id}/testCases`).then
		(response =>{
			this.setState({testCases:response.data})
			console.log(this.state.testCases)
		})
		.catch(err =>{
			console.log(err)
		})
	}

	handleSelect=(e)=>{
		console.log(e)
		this.setState({ language: e })
		console.log(this.state.language);
	}

	handleFileChange = e => {
		e.preventDefault();
			this.setState({ solution : e.target.files[0] })
		console.log(this.state.solution)
	}

	addSolution = e =>{
	let userId = localStorage.getItem('user_id');
	let problemId = this.state.problems.id
    e.preventDefault();
    const formdata = new FormData();
    formdata.append("solution",this.state.solution)

    axios.post(`${backend_url}/user/${userId}/problems/${problemId}/addSolution?language=${this.state.language}`,formdata).then(
      (response) => {
		alert(response)
      },
      (err) => {
        alert(err);
      }
    )

	
	
	}

	render() {
		
		const { problems,testCases } = this.state

		const pDescriptionStyle = {
			align: "left",
			border: "3px solid",
			borderRadius: "10px",
			fontSize: "20px",
			padding: "10px 10px",
        };
        const submitButtonStyle = {
            fontSize: "24px",
            backgroundColor: "indigo",
            color: "white",
            padding: "auto",
            marginTop: "15px",
        }
        const codepenStyle = {
            marginTop: "10px",
            marginBottom: "10px",
            padding: "5px 5px",
        }
        const formgrpStyle = {
            marginTop: "10px"
        }
		

		return (
			<Container>
				
			<div>
				<p>
					<h3>{ problems.title }</h3>
				</p>
				<div style={pDescriptionStyle} align="left">
					{problems.statement}
				</div>
			</div>

				<h3 style={codepenStyle} align="left">Your CodePen</h3>
				{/* <CodeEditor /> */}
				<SimpleEditor />
				<Form onSubmit={this.addSolution}> 
					<FormGroup style={formgrpStyle} align="left">
						<DropdownButton
							alignRight
							title="Choose Language"
							id="dropdown-menu-align-right"
							onSelect={this.handleSelect}
						>
							<Dropdown.Item eventKey="python2">Python2</Dropdown.Item>
							<Dropdown.Item eventKey="python3">Python3</Dropdown.Item>
							<Dropdown.Item eventKey="java">Java</Dropdown.Item>
							<Dropdown.Item eventKey="C">C</Dropdown.Item>
							<Dropdown.Item eventKey="CPP">C++</Dropdown.Item>
						</DropdownButton>
					</FormGroup>

					<FormGroup style={formgrpStyle} align="left">
						<Label for="exampleFile"><h5>Code File</h5></Label>
						<Input type="file" name="file" id="exampleFile" onChange={this.handleFileChange}/>
						<FormText  color="muted">
							Upload your code file here.
						</FormText>
                    </FormGroup>
                    <Button style={submitButtonStyle} type="submit">Submit</Button>
				</Form>

				<div>
					<Jumbotron fluid>
						<Container fluid>
					<h3 className="display-3">Sample Test Cases</h3>
					{
						testCases.length ?
						testCases.map(testCase =>
							<div key = {testCase.id} className="display-5">
								<h4 className="display-4">Input </h4>
								{testCase.input}
								<h4 className="display-4">Output</h4>
								{testCase.output}
							</div>	
						):null
					}
						</Container>
					</Jumbotron>
				</div>
				
			</Container>
		);
	}
}

export default ProblemDefinitionPage;
