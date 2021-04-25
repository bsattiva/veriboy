import React, {useRef, Suspense,  useEffect, useState, useMemo} from 'react'
import {Canvas, useFrame, useThree } from 'react-three-fiber'
import * as THREE from "three";
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import Grid from '../components/world/Grid'
import House from '../components/world/House'
import Houses from '../components/world/Houses'
import Flags from '../components/world/Flags'
import './page.css';

const Scena = (props) => {

const style = {
    width: '100%',
    height: '85vh',
    background: 'black'
}

var [position, setPosition] = useState([5,-0.03,1])
var [dir, setDir] = useState(new THREE.Vector3(3,0,1));
var [speed, setSpeed] = useState(0);
var [aspect, setAspect] = useState(0);
var [vaspect, setVaspect] = useState(0);
var [rad, setRad] = useState(0.08);

var [grids, setGrids] = useState([]);
var [flags, setFlags] = useState([[5,-1],[5,0],[5,1],[5,2],[5,3],[5,4],[5,5],[5,6],[5,7],[5,8],[5,9],[5,10],[5,11]
,[6,0.3],[4,0.3],[4,1.8],[6,1.8],[4,3.3],[6,3.3],[4,4.8],[6,4.8],[4,6.3],[6,6.3],[4,7.8],[6,7.8],[4,9.3],[6,9.3],[4,10.8],[6,10.8]]);

var [map, setMap] = useState({urls:["/house1.glb", "/house1.glb", "/house1.glb", "/house1.glb","/house2.glb", "/house2.glb", "/house2.glb", "/house2.glb"], 
pos: [[3,-0.5,1],[3,-0.5,4],[3,-0.5,7],[3,-0.5,10],[7,-0.5,1],[7,-0.5,4],[7,-0.5,7],[7,-0.5,10]]});


const Building = (props) => {
    const [house, set] = useState();
    useMemo(() => new GLTFLoader().load("../../textures/models/house.glb", set));

    return house ? <primitive object={house.scene} position={props.position}/> : null;
}

function DirectionalLight() {
    return (
        <directionalLight
        intensity={3.5}
        position={1,5,2}
        color={"#FFFFFF"}
        castShadow
        shadow-mapSize-height={112}
        shadow-mapSize-width={112}
        />
    )
}



function Light({ brightness, color }) {
    return (
      <hemisphereLight
        width={3}
        height={3}
        intensity={brightness}
        color={color}
        position={[5, 1, 4]}
        lookAt={[0, 0, 0]}
        penumbra={2}
        castShadow
      />
    );
  } 

const keydown = (event) => {
    var key = event.key;

    if(key === 'ArrowUp') {
        setSpeed(0.1);

    }
    if(key === 'ArrowDown') {
        setSpeed(-0.1);
    }
    if(key === 'ArrowLeft') {
        setAspect(aspect + rad);
    }
    if(key === 'ArrowRight') {
        setAspect(aspect - rad);
    }
    if(key === 'q') {
        setVaspect(vaspect + rad)
    }
    if(key === 'w') {
        setVaspect(vaspect - rad)
    } 


}

const keyup = (event) => {

    setSpeed(0);

}



const Camera = (props) => {
    const ref = useRef();
    const {setDefaultCamera} = useThree()

    useEffect(() => void setDefaultCamera(ref.current), [])

    useFrame(() => {
        if (ref.current) {
            ref.current.rotation.y = aspect;
            ref.current.rotation.x = vaspect;
            ref.current.getWorldDirection(dir);
            ref.current.position.addScaledVector(dir, speed);
            setPosition(ref.current.position); 
        }
        

    })

    return <perspectiveCamera ref={ref} {...props} />
}

const houses = [[6,-0.5,4],[8,-0.5,4]] 


    return (
        <Canvas colorManagement shadowMap style={style} tabIndex="0" onKeyDown={keydown} onKeyUp={keyup}>
            <Camera position={position} />
            <Light brightness={0.7} color={"#FFFFFF"}  />
            <DirectionalLight />
            <Suspense fallback={<>Loading</>}>
                <Grid width={10} length={10} flags={flags} />
            </Suspense> 
            <Suspense fallback={<>Loading</>}>
                <Houses map={map} />
            </Suspense>
            <Suspense  fallback={<>Loading</>} >
                <Flags flags={flags}/>
            </Suspense>
            
            
        </Canvas>
    )
}

export default Scena
 