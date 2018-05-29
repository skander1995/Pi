<?php

namespace EvenementBundle\Controller;

use EvenementBundle\Entity\Evenement;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use EvenementBundle\Entity\Reservation;

class DefaultController extends Controller
{
    public function newEvtFromJsonAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        // $em = $this->getDoctrine()->getManager();
        $event = new Evenement();
        $event->setPlaceDispo($request->get('placedispo'));
        $event->setIdUsr($this->getDoctrine()->getRepository('AdminUserBundle:User')->find($request->get('iduser')));
        $event->setPlaceDispo($request->get('placedispo'));
        $event->setLieu($request->get('lieu'));
        $event->setNomEvent($request->get('titre'));
        $event->setDescription($request->get('desc'));
        $event->setDatepub(new \DateTime());
        $event->setDatedebut(new \DateTime($request->get('dated')));
        $event->setDatefin(new \DateTime($request->get('datef')));
        $event->setAffiche($request->get('affiche'));



        $em->persist($event);
        $em->flush();

        // return result
        $serializedEntity = $this->container->get('serializer')->serialize($event, 'json');
        if ($event != null) {
            return new Response($serializedEntity);
        } else {
            return new JsonResponse("fail");
        }
    }

    public function indexJsonAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $evenements = $em->getRepository('EvenementBundle:Evenement')->findAll();

        $serializedEntity = $this->container->get('serializer')->serialize($evenements, 'json');
        if ($evenements != null) {
            return new Response($serializedEntity);
        } else {
            return new JsonResponse("fail");
        }

    }

    /**
     * @Route("/event/uploadphoto/", name="uploadable")
     */
    public function uploadPhotoAction( Request $request)
    {
        $allowedExts = array("gif", "jpeg", "jpg", "png");
        $temp = explode(".", $_FILES["file"]["name"]);
        $extension = end($temp);
        if ((($_FILES["file"]["type"] == "image/gif") || ($_FILES["file"]["type"] == "image/jpeg") || ($_FILES["file"]["type"] == "image/jpg")|| ($_FILES["file"]["type"] == "image/JPG")|| ($_FILES["file"]["type"] == "image/PNG") || ($_FILES["file"]["type"] == "image/pjpeg") || ($_FILES["file"]["type"] == "image/x-png") || ($_FILES["file"]["type"] == "image/png")) && ($_FILES["file"]["size"] < 5000000) && in_array($extension, $allowedExts)) {
            if ($_FILES["file"]["error"] > 0) {
                $named_array = array("Status" => "error");
                // echo json_encode($named_array);

                return new Response("error");

            } else {

                $uploaddir = realpath('./') . '/';
                $dir = "C:/wamp/www";
                $serdir = "/piproj/web/uploads/images/";
                $uploadfile = $dir . $serdir . basename($_FILES['file']['name']);
                move_uploaded_file($_FILES["file"]["tmp_name"], $uploadfile);

                $Path = $_FILES["file"]["name"];
                $named_array = array("Status" => "ok");
                return new Response("ok");
            }
        } else {
            $named_array = array("Status" => "invalid");
            //echo json_encode($named_array);
            return new Response("invalid");
        }

    }

    public function deleteJsonAction(Request $request)
    {
        $id = $request->get('id');
        $em = $this->getDoctrine()->getManager();
        $evenement= $em->getRepository('EvenementBundle:Evenement')->find($id);
        $em->remove($evenement);
        $em->flush();
        return new JsonResponse("deleted");

    }

    public function participerJsonAction( Request $request ){

        $evenement= $request->get('ev');
        var_dump($evenement);
        $em = $this->getDoctrine()->getManager();

        $event = $em->getRepository('EvenementBundle:Evenement')->find($evenement);

        $userId = $request->get('id');
        $user= $em->getRepository('AdminUserBundle:User')->find($userId);

        if($event->getPlaceDispo()>0){
            $reservation = new Reservation();
            $reservation->setIdUsr($user);
            $reservation->setIdPub($event);
            $em->persist($reservation);
            $em->flush();
            $event->setPlaceDispo($event->getPlaceDispo()-1);
            $em->persist($event);
            $em->flush();
            return new JsonResponse("true");
        }
        return new JsonResponse("false");
    }

    public function annulerparticipationJsonAction( Request $request){
        $evenement= $request->get('ev');
        $em = $this->getDoctrine()->getManager();
        $userId = $request->get('id');
        $user= $em->getRepository('AdminUserBundle:User')->find($userId);

        $reservation = $em->getRepository('EvenementBundle:Reservation')->findOneBy(array('idPub'=>$evenement ,'idUsr'=>$userId));

        $em->remove($reservation);
        $em->flush();
        $event = $em->getRepository('EvenementBundle:Evenement')->find($evenement);
        $event->setPlaceDispo($event->getPlaceDispo()+1);
        $em->persist($event);
        $em->flush();
        return new JsonResponse("true");
    }

    public function checkJsonAction(Request$request)
    {

        $part=3;
        $ev= $request->get('ev');
        $em = $this->getDoctrine()->getManager();
        $evenement= $em->getRepository('EvenementBundle:Evenement')->find($ev);
        $userId = $request->get('id');
        $evenement= $em->getRepository('EvenementBundle:Evenement')->find($ev);
        $user= $em->getRepository('AdminUserBundle:User')->find($userId);



        $em = $this->getDoctrine()->getManager();
        $reservation = $em->getRepository('EvenementBundle:Reservation')->findOneBy(
            array('idPub' => $ev , 'idUsr'=>$userId)
        );

        if(empty($reservation)){
            return new JsonResponse("false");
        }
        else{
            return new JsonResponse("true");
        }



    }
}
