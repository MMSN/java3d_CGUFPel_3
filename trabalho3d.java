import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;
import java.util.Hashtable;
import java.util.Enumeration;


/**
*
* @author Mateus M. S. do Nascimento
* Last change 17.07.2005
*/
public class trabalho3d extends JFrame
{

	//The canvas to be drawn upon.
	public Canvas3D myCanvas3D;

	public trabalho3d()
	{
    	//Mechanism for closing the window and ending the program.
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    //Default settings for the viewer parameters.
    	myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

	    //Construct the SimpleUniverse:
    	//First generate it using the Canvas.
    	SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);

	    //Default position of the viewer.
    	simpUniv.getViewingPlatform().setNominalViewingTransform();

	    //The scene is generated in this method.
    	createSceneGraph(simpUniv);

	    //Add some light to the scene.
    	addLight(simpUniv);

	    //The following three lines enable navigation through the scene using the mouse.
    	OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
	    ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
    	simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);

	    //Show the canvas/window.
    	setTitle("An image as a background");
    	setSize(1024,857);
    	getContentPane().add("Center", myCanvas3D);
    	setVisible(true);

	}

	public static void main(String[] args)
	{
    	trabalho3d be = new trabalho3d();
	}

	//In this method, the objects for the scene are generated and added to 
	//the SimpleUniverse.
	public void createSceneGraph(SimpleUniverse su)
	{
		float cubeEdge = 0.2f;//Half edge length of the transparent cube.
	    float transparencyCoefficient = 0.8f;//Transparency coefficient.
		Color3f ambientColourFBox = new Color3f(0.3f,0.0f,0.0f);
	    Color3f emissiveColourFBox = new Color3f(0.0f,0.0f,0.0f);
	    Color3f diffuseColourFBox = new Color3f(0.6f,0.0f,0.0f);
	    Color3f specularColourFBox = new Color3f(0.8f,0.0f,0.0f);
	    float shininessFBox = 128.0f;


		//The Appearance for the left cube.
    	Appearance fBoxApp1 = new Appearance();

    	fBoxApp1.setMaterial(new Material(ambientColourFBox,emissiveColourFBox,
                          diffuseColourFBox,specularColourFBox,shininessFBox));

	    //Generate interpolated transparency.
	    TransparencyAttributes ta1 = new TransparencyAttributes();
	    ta1.setTransparencyMode(TransparencyAttributes.BLENDED);
	    ta1.setTransparency(transparencyCoefficient);

	    fBoxApp1.setTransparencyAttributes(ta1);


	    //Generate a cube with interpolated transparency.
	    Box fBox1 = new Box(cubeEdge,cubeEdge,cubeEdge,fBoxApp1);

		//Position the cube.
	    Transform3D tfFBox1 = new Transform3D();
	    Transform3D rotationX = new Transform3D();
	    rotationX.rotX(0);
	    tfFBox1.mul(rotationX);
		tfFBox1.setTranslation(new Vector3f(-4.0f,-6.4f,-20.0f));

	    //The transformation group for the cube.
	    TransformGroup tgFBox1 = new TransformGroup(tfFBox1);
	    tgFBox1.addChild(fBox1);


		//Load an obj-file.
	    ObjectFile fPig = new ObjectFile(ObjectFile.RESIZE);
    	Scene s = null;

	    try
    	{
    		s = fPig.load("Hamm_high.obj");
    	}
    	catch (Exception e)
    	{
    		System.out.println("File loading failed:" + e);
    	}

	    //Generate a transformation group for the loaded object.
	    Transform3D tfPigObject = new Transform3D();
	    tfPigObject.rotZ(0.45*Math.PI);
	    Transform3D xRotationPig = new Transform3D();
	    xRotationPig.rotY(0.45*Math.PI);
	    tfPigObject.mul(xRotationPig);
	    
	    tfPigObject.setTranslation(new Vector3f(-4.0f,-4.4f,-20.0f));
	    
	    TransformGroup tgPigObject = new TransformGroup(tfPigObject);
	    tgPigObject.addChild(s.getSceneGroup());
	
		//In the following way, the names of the parts of the object can be
	    //obtained. The names are printed.
	    Hashtable namedObjects = s.getNamedObjects();
	    Enumeration enumer = namedObjects.keys();
	    String name;
	    while (enumer.hasMoreElements())
	    {
	    	name = (String) enumer.nextElement();
      		System.out.println("Name: "+name);
    	}
    	/*
    	Appearance PinkApp = new Appearance();
    	Color3f colPink = new Color3f(0.0f,0.1f,0.3f);
    	//setToMyDefaultAppearance(PinkApp,new Color3f(0.0f,0.1f,0.3f));
    	ColoringAttributes ca = new ColoringAttributes(colPink, ColoringAttributes.NICEST);
    	PinkApp.setColoringAttributes(ca);
    	Shape3D partOfTheObject = (Shape3D) namedObjects.get("hamm_high_0");
    	partOfTheObject.setAppearance(PinkApp);
		*/
		//Load an obj-file.
	    ObjectFile fCat = new ObjectFile(ObjectFile.RESIZE);
    	//Scene s = null;

	    try
    	{
    		s = fCat.load("cat.obj");
    	}
    	catch (Exception e)
    	{
    		System.out.println("File loading failed:" + e);
    	}

	    //Generate a transformation group for the loaded object.
	    Transform3D tfCatObject = new Transform3D();
	    tfCatObject.rotZ(0.0*Math.PI);
	    Transform3D xRotationCat = new Transform3D();
	    xRotationCat.rotY(0.4*Math.PI);
	    tfCatObject.mul(xRotationCat);
	    
	    tfCatObject.setTranslation(new Vector3f(1.75f,-2.75f,-7.0f));
	    
	    TransformGroup tgCatObject = new TransformGroup(tfCatObject);
	    tgCatObject.addChild(s.getSceneGroup());
	
		//Load an obj-file.
	    ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
    	//Scene s = null;

	    try
    	{
    		s = f.load("FirePlace.obj");
    	}
    	catch (Exception e)
    	{
    		System.out.println("File loading failed:" + e);
    	}

	    //Generate a transformation group for the loaded object.
	    Transform3D tfObject = new Transform3D();
	    tfObject.rotZ(0.0*Math.PI);
	    Transform3D xRotation = new Transform3D();
	    xRotation.rotY(0.0*Math.PI);
	    tfObject.mul(xRotation);
	    
	    tfObject.setTranslation(new Vector3f(1.75f,-1.75f,-7.0f));
	    
	    TransformGroup tgObject = new TransformGroup(tfObject);
	    tgObject.addChild(s.getSceneGroup());

		//Load the image for the texture.
		TextureLoader textureSphereLoad = new TextureLoader("cordas.jpg",null);

		//Generate a (scaled) image of the texture. Height and width must be
		//powers of 2.
		ImageComponent2D textureSphereIm = textureSphereLoad.getScaledImage(512,512);

		//Generate the texture.
		Texture2D aSphereTexture = new Texture2D(Texture2D.BASE_LEVEL,Texture2D.RGB,
                                            textureSphereIm.getWidth(),
                                            textureSphereIm.getHeight());
		aSphereTexture.setImage(0,textureSphereIm);

		//An Appearance which will use the texture.
		Appearance textureSphereApp = new Appearance();
		textureSphereApp.setTexture(aSphereTexture);
		TextureAttributes textureSphereAttr = new TextureAttributes();
		textureSphereAttr.setTextureMode(TextureAttributes.REPLACE);
		textureSphereApp.setTextureAttributes(textureSphereAttr);
		Material Spheremat = new Material();
		Spheremat.setShininess(120.0f);
		textureSphereApp.setMaterial(Spheremat);
		TexCoordGeneration tcgSphere = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR,
                                                    TexCoordGeneration.TEXTURE_COORDINATE_2);

		textureSphereApp.setTexCoordGeneration(tcgSphere);


		TextureLoader textureSphereLoad2 = new TextureLoader("textura2.jpg",null);

		//Generate a (scaled) image of the texture. Height and width must be
		//powers of 2.
		ImageComponent2D textureSphereIm2 = textureSphereLoad2.getScaledImage(512,512);

		//Generate the texture.
		Texture2D aSphereTexture2 = new Texture2D(Texture2D.BASE_LEVEL,Texture2D.RGB,
                                            textureSphereIm2.getWidth(),
                                            textureSphereIm2.getHeight());
		aSphereTexture2.setImage(0,textureSphereIm2);

		//An Appearance which will use the texture.
		Appearance textureSphereApp2 = new Appearance();
		textureSphereApp2.setTexture(aSphereTexture2);
		TextureAttributes textureSphereAttr2 = new TextureAttributes();
		textureSphereAttr2.setTextureMode(TextureAttributes.REPLACE);
		textureSphereApp2.setTextureAttributes(textureSphereAttr);
		Material Spheremat2 = new Material();
		Spheremat2.setShininess(120.0f);
		textureSphereApp2.setMaterial(Spheremat2);
		TexCoordGeneration tcgSphere2 = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR,
                                                    TexCoordGeneration.TEXTURE_COORDINATE_2);

		textureSphereApp2.setTexCoordGeneration(tcgSphere2);



		/*
		//*** And a sphere with its transformation group.
	    //The colours for the Appearance.
	    Color3f ambientColourBSphere = new Color3f(0.0f,0.4f,0.0f);
	    Color3f emissiveColourBSphere = new Color3f(0.0f,0.0f,0.0f);
	    Color3f diffuseColourBSphere = new Color3f(0.0f,0.0f,0.0f);
	    Color3f specularColourBSphere = new Color3f(0.0f,0.8f,0.0f);
	    float shininessBSphere = 128.0f;

	    //The Appearance for the sphere.
	    Appearance bSphereApp = new Appearance();
	    bSphereApp.setMaterial(new Material(ambientColourBSphere,emissiveColourBSphere,
                          diffuseColourBSphere,specularColourBSphere,shininessBSphere));
		*/

	    //The sphere.
	    Sphere bSphere = new Sphere(0.2f,Sphere.GENERATE_NORMALS,100,textureSphereApp);

		bSphere.setCollisionBounds(new BoundingSphere(new Point3d(2.5f,-3.5f,-7.5f),
                                            Double.MAX_VALUE));
	    bSphere.setCollidable(true);


	    //The transformation group for the sphere.
	    Transform3D tfBSphere = new Transform3D();
	    tfBSphere.setTranslation(new Vector3f(0.0f,0.0f,0.0f));
	    TransformGroup tgBSphere = new TransformGroup(tfBSphere);
	    tgBSphere.addChild(bSphere);

		//This transformation group is needed for the movement of the cylinder.
	    TransformGroup tgmSphere = new TransformGroup();
	    tgmSphere.addChild(tgBSphere);


		Sphere bSphere2 = new Sphere(0.2f,Sphere.GENERATE_NORMALS,100,textureSphereApp2);

		bSphere2.setCollisionBounds(new BoundingBox(new Point3d(0.0f,0.0f,0.0f),
                                            new Point3d(0.1,0.15,0.1)));
    	bSphere2.setCollidable(true);

	    //The transformation group for the sphere.
	    Transform3D tfBSphere2 = new Transform3D();
	    tfBSphere2.setTranslation(new Vector3f(4.5f,-3.5f,-7.5f));
	    TransformGroup tgBSphere2 = new TransformGroup(tfBSphere2);
	    tgBSphere2.addChild(bSphere2);

		//This transformation group is needed for the movement of the cylinder.
	    TransformGroup tgmSphere2 = new TransformGroup();
	    tgmSphere2.addChild(tgBSphere2);

		//The movement from left to right.
	    Transform3D escape = new Transform3D();
	    Alpha sphereAlphaR = new Alpha(1,2000);

	    //The starting time is first postponed until "infinity".
	    sphereAlphaR.setStartTime(Long.MAX_VALUE);

	    //The interpolator for the movement.
	    float maxRight = 0.5f;
	    PositionInterpolator sphereMoveR = new PositionInterpolator(sphereAlphaR,tgmSphere2,
                                                             escape,0.0f,maxRight);

	    BoundingSphere gbounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
	    sphereMoveR.setSchedulingBounds(gbounds);
	
		//Add the movements to the transformation group.
    	tgmSphere2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    	tgmSphere2.addChild(sphereMoveR);
		
		//A corresponding class for the movement(s) of the cylinder.
	    Alpha[] sphereAlphas= new Alpha[1];
	    sphereAlphas[0] = sphereAlphaR;


	    //In the following way, the names of the parts of the object can be
	    //obtained. The names are printed.
	    namedObjects = s.getNamedObjects();
	    enumer = namedObjects.keys();
	    //String name;
	    while (enumer.hasMoreElements())
	    {
	    	name = (String) enumer.nextElement();
      		System.out.println("Name: "+name);
    	}

		/*
    	//Assign the colour blue to the part named "fireplace".
    	Appearance lightBlueApp = new Appearance();
    	setToMyDefaultAppearance(lightBlueApp,new Color3f(0.0f,0.1f,0.3f));
    	Shape3D partOfTheObject = (Shape3D) namedObjects.get("fireplace");
    	partOfTheObject.setAppearance(lightBlueApp);
		*/

	BoundingSphere fbounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
	//BoundingSphere gbounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);

	//Position Interpolator no objeto cat
    Alpha alphaCat = new Alpha(-1,12000);
    Transform3D axisOfTranslation = new Transform3D();
    //axisOfTranslation.rotY(0);
    //axisOfTranslation.setTranslation(new Vector3f(0.0f,0.0f,20.0f));
	
    //Pontos de interpolação das positions
    float[] knots = { 0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f };
    Point3f[] positions = new Point3f[11];
    Quat4f[] quats = new Quat4f[11];
    AxisAngle4f axis = new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f);
    axisOfTranslation.set(axis);

    quats[0] =  new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    quats[1] =  new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    quats[2] =  new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    quats[3] =  new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    quats[4] =  new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    quats[5] =  new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    quats[6] =  new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    quats[7] =  new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    quats[8] =  new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    quats[9] =  new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    quats[10] = new Quat4f(0.0f, 0.6f, 0.0f, 1f);
    
    //Linha no eixo tridimensional que vai ser o PATH a ser percorrido
    positions[0] =  new Point3f(1.25f,-2.75f,-7.0f);
    positions[1] =  new Point3f(1.3f ,-2.75f,-7.0f);
    positions[2] =  new Point3f(1.35f,-2.75f,-7.0f);
    positions[3] =  new Point3f(1.4f ,-2.75f,-7.0f);
    positions[4] =  new Point3f(1.45f,-2.75f,-7.0f);
    positions[5] =  new Point3f(1.5f ,-2.75f,-7.0f);
    positions[6] =  new Point3f(1.55f,-2.75f,-7.0f);
    positions[7] =  new Point3f(1.6f ,-2.75f,-7.0f);
    positions[8] =  new Point3f(1.65f,-2.75f,-7.0f);
    positions[9] =  new Point3f(1.7f ,-2.75f,-7.0f);
    positions[10] = new Point3f(1.75f,-2.75f,-7.0f);
    //Path Interpolator
    RotPosPathInterpolator cat_interpolator = new RotPosPathInterpolator(alphaCat,tgCatObject,axisOfTranslation,knots, quats, positions);
	
    //Adiciona Animação ao TransformGroup do cat
    cat_interpolator.setSchedulingBounds(fbounds);
    tgCatObject.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tgCatObject.addChild(cat_interpolator);

	
	
	Alpha alphaSphere = new Alpha(-1,12000);
    Transform3D axisOfTranslation2 = new Transform3D();
	float[] knots2 = { 0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f };
	Point3f[] positions2 = new Point3f[11];
    Quat4f[] quats2 = new Quat4f[11];
    AxisAngle4f axis2 = new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f);
    axisOfTranslation2.set(axis2);

    quats2[0] =  new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    quats2[1] =  new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    quats2[2] =  new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    quats2[3] =  new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    quats2[4] =  new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    quats2[5] =  new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    quats2[6] =  new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    quats2[7] =  new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    quats2[8] =  new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    quats2[9] =  new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    quats2[10] = new Quat4f(0.0f, 0.0f, 0.0f, 1f);
    
    //Linha no eixo tridimensional que vai ser o PATH a ser percorrido
    positions2[0] =  new Point3f(2.5f,-3.5f,-7.5f);
    positions2[1] =  new Point3f(2.7f,-3.5f,-7.5f);
    positions2[2] =  new Point3f(2.9f,-3.5f,-7.5f);
    positions2[3] =  new Point3f(3.1f,-3.5f,-7.5f);
    positions2[4] =  new Point3f(3.3f,-3.5f,-7.5f);
    positions2[5] =  new Point3f(3.5f,-3.5f,-7.5f);
    positions2[6] =  new Point3f(3.7f,-3.5f,-7.5f);
    positions2[7] =  new Point3f(3.9f,-3.5f,-7.5f);
    positions2[8] =  new Point3f(4.1f,-3.5f,-7.5f);
    positions2[9] =  new Point3f(4.3f,-3.5f,-7.5f);
    positions2[10] = new Point3f(4.5f,-3.5f,-7.5f);
    //Path Interpolator
    RotPosPathInterpolator sphere_interpolator = new RotPosPathInterpolator(alphaSphere,tgmSphere,axisOfTranslation2,knots2, quats2, positions2);
	
    //Adiciona Animação ao TransformGroup do cat
    sphere_interpolator.setSchedulingBounds(gbounds);
    tgmSphere.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tgmSphere.addChild(sphere_interpolator);
	
		
	    CollisionBehaviour2 scb2 = new CollisionBehaviour2(bSphere2,sphereAlphas,fbounds);


	    BranchGroup theScene = new BranchGroup();

		theScene.addChild(tgmSphere);
	    theScene.addChild(tgmSphere2);
	    theScene.addChild(tgObject);
	    theScene.addChild(tgCatObject);
		theScene.addChild(tgPigObject);
		theScene.addChild(scb2);
		theScene.addChild(tgFBox1);

	    //The bounding region for the background.
    	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
    	//Load the background image.
	    TextureLoader textureLoad = new TextureLoader("fundo.jpg",null);
    	//Define the image as the background and add it to the scene.
	    Background bgImage = new Background(textureLoad.getImage());
    	bgImage.setApplicationBounds(bounds);
	    theScene.addChild(bgImage);

	    theScene.compile();

	    //Add the scene to the universe.
    	su.addBranchGraph(theScene);

	}

	//Some light is added to the scene here.
	public void addLight(SimpleUniverse su)
	{
		BranchGroup bgLight = new BranchGroup();

		
    	//Directional light.
    	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);
    	Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
    	Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.1f);
    	DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    	light1.setInfluencingBounds(bounds);

    	bgLight.addChild(light1);

    

    	//Directional light (to be rotated).
    	Color3f lightColour = new Color3f(0.2f, 0.1f, 0.1f);
    	Vector3f lightDir  = new Vector3f(0.0f, 0.0f, -1.0f);
    	DirectionalLight light = new DirectionalLight(lightColour, lightDir);
    	light.setInfluencingBounds(bounds);

    	//The transformation group for the directional light and its rotation.
    	TransformGroup tfmLight = new TransformGroup();
    	tfmLight.addChild(light);

    	//The Alpha for the rotation.
    	Alpha alphaLight = new Alpha(-1,4000);
    	//The rotation
    	RotationInterpolator rot = new RotationInterpolator(alphaLight,tfmLight,
                                                        new Transform3D(),
                                                         0.0f,(float) Math.PI*2);
    	rot.setSchedulingBounds(bounds);

    	tfmLight.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    	tfmLight.addChild(rot);

    	bgLight.addChild(tfmLight);

	    su.addBranchGraph(bgLight);

  	}

}
