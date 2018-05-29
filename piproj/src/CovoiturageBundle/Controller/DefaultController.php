<?php

namespace CovoiturageBundle\Controller;

use AdminUserBundle\Entity\User;
use CovoiturageBundle\Entity\Covoiturage;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;


use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;


class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('CovoiturageBundle:Default:index.html.twig');
    }

    public function newCovoiturageJsonAction(Request $request)
    {

        $em = $this->getDoctrine()->getManager();
        $repo = $em->getRepository('CovoiturageBundle:Covoiturage');
        $covoiturage = new Covoiturage();
        $finduser = new User();
        $id = $request->get('id');
        $finduser = $em->getRepository('AdminUserBundle:User')->find($id);
        var_dump($request->get('dated'));
        $timestamp = $request->get('dated');
        $startTime = new \DateTime();
        //$startTime->setTimestamp($timestamp);
        //$startTime->format("d-m-Y");
        $f = $startTime->format('Y-m-d');
        $covoiturage->setDatedepart($startTime);
        $covoiturage->setLieudepart($request->get('lieudepart'));
        $covoiturage->setLieuarrive($request->get('lieuarrive'));
        $covoiturage->setNbplace($request->get('nbplace'));
        $covoiturage->setPrix($request->get('prix'));
        $covoiturage->setDescription($request->get('description'));
        $covoiturage->setIdUsr($id);

        $em->persist($covoiturage);
        $em->flush();

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($covoiturage);

        return new JsonResponse($formatted);

    }

    public function getCovoiturageJsonAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $covoiturage = $em->getRepository('CovoiturageBundle:Covoiturage')->find($id);

        $serializedEntity = $this->container->get('serializer')->serialize($covoiturage, 'json');
        if ($covoiturage != null) {
            return new Response($serializedEntity);
        } else {
            return new JsonResponse("not found");
        }
    }

    public function getAllCovoiturageJsonAction()
    {
        $em = $this->getDoctrine()->getManager();
        $covoiturages = $em->getRepository('CovoiturageBundle:Covoiturage')->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($covoiturages);
        return new JsonResponse($formatted);
    }
}
