import axios from 'axios'
import React, { Component } from 'react'
import { Form,Icon } from 'react-bootstrap'
import { Link, Redirect } from "react-router-dom"
import backend_url from '../services/api'
import Navbar from './Navbar'

class AddProblem extends Component {

  constructor(){
    super()
    this.state = {
			
			redirect: null,
      title:'',
      statement:'',
      score:'',
      numOfTestCases:'',
      category:'',
      difficulty:''
		};
  }
		
  handleChange = e => {
		//console.log(e.target.name)
		this.setState({ [e.target.name]: e.target.value })
	}

  handleFileChange = e => {
    e.preventDefault();
		//console.log(e.target.files[0])
    //console.log(typeof(e.target.files))
		this.setState({ solution : e.target.files[0] })
    //console.log(this.state.solution)
	}

  addTestCases = (e) =>{
    let userId = localStorage.getItem('user_id');
    e.preventDefault();
    const formdata = new FormData();
    formdata.append("title",this.state.title)
    formdata.append("statement",this.state.statement)
    formdata.append("solution",this.state.solution)
    formdata.append("score",this.state.score)
    formdata.append("numOfTestCases",this.state.numOfTestCases)
    formdata.append("difficulty",this.state.difficulty)
    formdata.append("category",this.state.category)

    axios.post(`${backend_url}/user/${userId}/addProblems`,formdata).then(
      (response) => {
        localStorage.setItem("problemId",response.data)
        localStorage.setItem("test_case_count",this.state.numOfTestCases)
        this.setState({redirect:"/addTestCases"})
      },
      (err) => {
        alert(err);
      }
    )
    
  }
  render() {
    if (this.state.redirect) {
			return <Redirect to={this.state.redirect} />
		  }

      const {title,statement,numOfTestCases,difficulty,category,score}=this.state

    return (
      <div className="main-panel" style={{ marginTop: 20, marginLeft: 100 }}>
        <Navbar />
        <div className="page-header">
          <h3 className="page-title">
            <span className="page-title-icon bg-gradient-primary text-white mr-2">
              <i className="mdi mdi-account-box"></i>
            </span>Add a problem</h3>
        </div>

        <div className="col-12 grid-margin stretch-card">
          <div className="card">
            <div className="card-body">
              <h4 className="card-title">Fill up the form</h4>
              <form className="forms-sample" onSubmit={this.addTestCases} encType="multipart/form-data">
                <Form.Group>
                  <label>Problem Title</label>
                  <Form.Control type="text" className="form-control" placeholder="Problem Title"
                   name="title"
                   value={title}
                   onChange={this.handleChange}
                   />
                </Form.Group>
                <Form.Group>
                  <label >Problem Statement</label>
                  <textarea placeholder="Problem Statement" className="form-control" rows="4"
                    name="statement"
                    value={statement}
                    onChange={this.handleChange}
                  ></textarea>
                </Form.Group>
              <Form.Group >
              <label>Solution File</label>
              <div className="custom-file"  >
                <Form.Control type="file" className="form-control visibility-hidden" id="customFileLang1" lang="es" 
                onChange={this.handleFileChange}/>
                <label className="custom-file-label" htmlFor="customFileLang1">Upload solution file</label>
              </div>
            </Form.Group>
                <Form.Group>
                  <label>Maximum Score</label>
                  <Form.Control type="number" className="form-control" placeholder="Maximum Score"
                  name="score"
                  value={score}
                  onChange={this.handleChange} />
                </Form.Group>
                <Form.Group>
                  <label>Number of test cases</label>
                  <Form.Control type="number" className="form-control" placeholder="Number of test cases" 
                  name="numOfTestCases"
                  value={numOfTestCases}
                  onChange={this.handleChange}/>
                </Form.Group>
                <Form.Group>
                  <label>Category</label>
                  <Form.Control type="text" className="form-control" placeholder="Problem Title"
                  name="category"
                  value={category}
                  onChange={this.handleChange} />
                </Form.Group>
                <Form.Group>
                  <label>Difficulty</label>
                  <Form.Control type="text" className="form-control" placeholder="Problem Title"
                  name="difficulty"
                  value={difficulty}
                  onChange={this.handleChange} />
                </Form.Group>
                <button type="submit" className="btn btn-gradient-primary mr-2">Next</button>
                <button className="btn btn-light">Cancel</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default AddProblem;

// Ram,Shyam and Gyan are playing a game. They all draw a random ball having different number written on it  from a black box. The one with the highest drawn number wins the game. Your task is to help Ram win the game by picking the largest number ball from the given balls.