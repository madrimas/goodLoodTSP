import React from 'react'
import Graph from 'vis-react';

class CustomGraph extends React.Component {
    constructor(props) {
        super(props) 
        this.state = {
            graph: null
        }
    }

    componentDidMount = () => {
        if(this.state.graph === null) {
            fetch('http://localhost:8080/graph')
            .then(response => response.json())
            .then(data => this.setState({graph: data}));
        }
    } 

    render() { 

        let options = {
            layout: {
              improvedLayout: true
            },
            edges: {
                color: '#000000'
            },
            interaction: { hoverEdges: true },
        
            physics: {enabled: false}
        };
         
        let events = {
            select: function(event) {
                var { nodes, edges } = event;
            }
        };

        const graph = this.state.graph
        if (graph === null) return <div />

        return(
            <Graph
            graph={graph}
            options={options}
        />
        )
    }

}

export default CustomGraph