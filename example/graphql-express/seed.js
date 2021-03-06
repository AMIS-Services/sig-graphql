import db from "./src/db";

const seed = async () => {
  const membersUi = [
    { name: "Bram" },
    { name: "Chiel" },
    { name: "Nathan" },
    { name: "Esmeralda" },
    { name: "Jeroen" },
    { name: "Nancy" },
    { name: "Matthijs" },
    { name: "Kjettil" },
    { name: "Mark" }
  ];

  const membersIntegration = [{ name: "Joost" }, { name: "Robert" }];

  const practices = [{ name: "ui" }, { name: "integratie" }];

  const projects = [{ name: "CIS" }, { name: "Flex-ID" }, { name: "Floriday Insights" }, { name: "Conclusion Flora" }];

  const cisMembers = ["Joost", "Mark"];
  const flexIdMembers = ["Matthijs", "Nancy", "Jeroen"];
  const rfhMembers = ["Bram", "Nathan"];
  const conclusionFloraMembers = ["Esmeralda", "Kjettil"];

  const uiPeopleEntities = await Promise.all(membersUi.map(async person => await db.person.create({ ...person })));
  const integrationPeopleEntities = await Promise.all(
    membersIntegration.map(async person => await db.person.create({ ...person }))
  );

  const allPersonEntities = [...uiPeopleEntities, ...integrationPeopleEntities];

  const practiceEntities = await Promise.all(
    practices.map(async practice => await db.practice.create({ ...practice }))
  );

  const projectEntities = await Promise.all(projects.map(async project => await db.project.create({ ...project })));

  // people <-> practices
  uiPeopleEntities.map(person => {
    person.practiceId = practiceEntities[0].dataValues.id;
    db.person.update(person.dataValues, { where: { id: person.dataValues.id } });
  });

  integrationPeopleEntities.map(person => {
    person.practiceId = practiceEntities[1].dataValues.id;
    db.person.update(person.dataValues, { where: { id: person.dataValues.id } });
  });

  // people <-> projects
  allPersonEntities
    .filter(person => cisMembers.includes(person.dataValues.name))
    .map(person => person.addProject(projectEntities[0]));

  allPersonEntities
    .filter(person => flexIdMembers.includes(person.dataValues.name))
    .map(person => person.addProject(projectEntities[1]));

  allPersonEntities
    .filter(person => rfhMembers.includes(person.dataValues.name))
    .map(person => person.addProject(projectEntities[2]));

  allPersonEntities
    .filter(person => conclusionFloraMembers.includes(person.dataValues.name))
    .map(person => person.addProject(projectEntities[3]));

  // practices <->projects
  projectEntities[0].addPractice(practiceEntities[0]);
  projectEntities[0].addPractice(practiceEntities[1]);
  projectEntities[1].addPractice(practiceEntities[0]);
  projectEntities[2].addPractice(practiceEntities[0]);
  projectEntities[3].addPractice(practiceEntities[0]);
};

seed();
