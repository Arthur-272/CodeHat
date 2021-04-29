import React, { useState } from 'react';
import { CKEditor } from '@ckeditor/ckeditor5-react';
import parse from "html-react-parser"
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import "../Discussion/Discussion.css"
import { Navbar } from 'react-bootstrap';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import { ProgressBar } from 'react-bootstrap';

function Discussion() {
    const [text, setText] = useState("")

    return (
    
        <div className="main-panel" >
            <div>
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item"><a href="#">Library</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Data</li>
                    </ol>
                </nav>
            </div>

            <div className="container float-left">
                <h1 className="font-weight-strong">Problem Title</h1>
            </div>

            <div class="container pl-5">
                <table border="1" >
                    <tr>
                        <td>
                            <Link><h2 className="font-weight-light pl-5 pr-5">Problem</h2></Link>
                        </td>
                        <td>
                            <Link to="/submission"><h2 className="font-weight-light pl-5 pr-5">Submissions</h2></Link>
                        </td>
                        <td>
                            <Link to="/discusison"><h2 className="font-weight-light pl-5 pr-5">Discussion</h2></Link>
                        </td>
                    </tr>
                </table>
            </div>
            
            <div className="Discussion">
                <div className="editor">
                    <CKEditor
                        editor={ClassicEditor}
                        data={text}
                        onChange={(event, editor) => {
                            const data = editor.getData()
                            setText(data)
                        }}
                    />
                </div>
                <br>
                </br>
                <div className="container pr-9 " >
                    <button className="btn btn-success btn-lg float-right" type="submit"><b>Submit</b></button>
                </div>
            </div>
        </div>

    )
}
export default Discussion

