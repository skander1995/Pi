<?php

namespace FaqBundle\Controller;

use FaqBundle\Entity\commentaire_ques;
use FaqBundle\Entity\Commentaire_question;
use FaqBundle\Entity\Question;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Serializer;

/**
 * Question controller.
 *
 */
class QuestionController extends Controller
{
    /**
     * Lists all question entities.
     *
     */
    public function findall_mobAction()
    {
        $em = $this->getDoctrine()->getManager();
        $questions = $em->getRepository('FaqBundle:Question')->findAll();

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($questions);
        return new JsonResponse($formatted);

    }

    /**
     * Creates a new question entity.
     *
     */
    public function new_mobAction($idusr,$desc)
    { $em = $this->getDoctrine()->getManager();

        $us = $em->getRepository('UserBundle:User')
            ->find($idusr);
        $question = new Question();
        $question->setIdUsr($us);
        $question->setSujet($desc);
        $question->setDescription($desc);
        $question->setDatepub($this->getDate());

            $em->persist($question);
            $em->flush();

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($question);
        return new JsonResponse($formatted);
    }

    /**
     * Finds and displays a question entity.
     *
     */
    public function fin_mobAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $questions = $em->getRepository('FaqBundle:Question')->findBy(array('idpub'=>$id));
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($questions);
        return new JsonResponse($formatted);

    }
    /**
     * Deletes a question entity.
     *
     */
    public function delete_mobAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $aide = $em->getRepository("FaqBundle:Question")->find($id);
        $em->remove($aide);
        $em->flush();

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($aide);
        return new JsonResponse($formatted);
        }


    public function All_commentsmobAction()
    {
        $task=$this->getDoctrine()->getManager()
            ->getRepository('FaqBundle:Commentaire_question')
            ->findAll();
        $serializer= new Serializer([new ObjectNormalizer()]);
        $formated= $serializer->normalize($task) ;
        return new JsonResponse($formated);
    }
    public function Add_commentmobAction($idusr,$idpub,$contenu)
    {

        $em = $this->getDoctrine()->getManager();
        $us = $em->getRepository('UserBundle:User')
            ->find($idusr);
        $commentaire = new Commentaire_question();
        $commentaire->setIdUsr($us);
        $commentaire->setIdPub($idpub);
        $commentaire->setDateCom($this->getDate());
        $commentaire->setContenuCom($contenu);

        $em = $this->getDoctrine()->getManager();
        $em->persist($commentaire);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($commentaire);
        return new JsonResponse($formatted);


    }
















    /**
     *
     * @return \DateTime
     */
    public function getDate()
    {
        return new \DateTime('now', (new \DateTimeZone('Africa/Tunis')));
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public function searchAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        if ($request->isXmlHttpRequest()) {
            $serializer = new Serializer(array(new ObjectNormalizer()));
            $questions = $em->getRepository("FaqBundle:Question")->findQuestion($request->request->get("data"));
            $data = $serializer->normalize($questions);
            return new JsonResponse($data);
        }
    }

    /**
     * Lists all question entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $questions = $em->getRepository('FaqBundle:Question')->findAll();

       /*
         return $this->render('@Faq/Default/faq.html.twig', array(
            'questions' => $questions,
        ));
       */

        return $this->render('question/index.html.twig', array(
            'questions' => $questions,
        ));
    }

    /**
     * Creates a new question entity.
     *
     */
    public function newAction(Request $request)
    {
        $question = new Question();
        $form = $this->createForm('FaqBundle\Form\QuestionType', $question);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($question);
            $em->flush();

            return $this->redirectToRoute('question_show', array('idPub' => $question->getIdpub()));
        }

        if ($request->isXmlHttpRequest()) {
            return new Response(
                $this->render('question/new.html.twig', array(
                    'question' => $question,
                    'form' => $form->createView(),
                ))->getContent());
        }


        return $this->render('question/new.html.twig', array(
            'question' => $question,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a question entity.
     *
     */
    public function showAction(Question $question)
    {
        $em = $this->getDoctrine()->getManager();
        $questions = $em->getRepository('FaqBundle:Question')->findAll();

        return $this->render('@Faq/Default/faq.html.twig', array(
            'questions' => $questions,
            'param' => $question
        ));

        /* orig */
        $deleteForm = $this->createDeleteForm($question);
        return $this->render('question/show.html.twig', array(
            'question' => $question,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing question entity.
     *
     */
    public function editAction(Request $request, Question $question)
    {
        $deleteForm = $this->createDeleteForm($question);
        $editForm = $this->createForm('FaqBundle\Form\QuestionType', $question);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('question_edit', array('idPub' => $question->getIdpub()));
        }

        return $this->render('question/edit.html.twig', array(
            'question' => $question,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a question entity.
     *
     */
    public function deleteAction(Request $request, Question $question)
    {
        $form = $this->createDeleteForm($question);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($question);
            $em->flush();
        }

        return $this->redirectToRoute('question_index');
    }

    /**
     * Creates a form to delete a question entity.
     *
     * @param Question $question The question entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Question $question)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('question_delete', array('idPub' => $question->getIdpub())))
            ->setMethod('DELETE')
            ->getForm();
    }
}
