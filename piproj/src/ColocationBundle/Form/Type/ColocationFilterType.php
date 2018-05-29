<?php

namespace ColocationBundle\Form\Type;

use Doctrine\ORM\Mapping\Entity;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\DateFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\EntityFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\NumberFilterType;
use Lexik\Bundle\FormFilterBundle\Filter\Form\Type\TextFilterType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ColocationFilterType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        
            ->add('idUsr', NumberFilterType::class)
            ->add('datepub', DateFilterType::class)
            ->add('description', TextFilterType::class)
            ->add('etat', TextFilterType::class)
            ->add('lieu', TextFilterType::class)
            ->add('loyermensuel', TextFilterType::class)
            ->add('typelogement', TextFilterType::class)
            ->add('typechambre', TextFilterType::class)
            ->add('imgcouverture', TextFilterType::class)
            ->add('nbchambre', NumberFilterType::class)
            ->add('commodite', TextFilterType::class)
            ->add('datedisponibilite', DateFilterType::class)
            ->add('idVille', EntityFilterType::class, array('class' => 'TmpBundle\Entity\Ville'))
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class'        => 'ColocationBundle\Entity\Colocation',
            'csrf_protection'   => false,
            'validation_groups' => array('filter'),
            'method'            => 'GET',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'colocation_filter';
    }
}
